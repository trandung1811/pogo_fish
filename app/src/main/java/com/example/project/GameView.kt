package com.example.project

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import java.lang.Math.abs
import kotlin.random.Random


class GameView(context: Context, resolutionPointSize: Point)
    : SurfaceView(context), Runnable {

    private val rocks_r: Array<Rock?> = arrayOfNulls(5)
    private val rocks_l: Array<Rock?> = arrayOfNulls(5)
    private var screenX = 0
    private var screenY = 0
    private var numb_left = 0
    private var numb_right = 0
    private val background1: Background
    private val background2: Background

    var screenRatioX: Float =   0f
    var screenRatioY: Float = 0f

    // This is needed for the OS to call run()
    private val gameThread: Thread = Thread(this)
    private var running: Boolean = true
    private var canvas: Canvas = Canvas()
    private var paint: Paint = Paint()
    private var new_x: Float = -1.1f
    private var new_y: Float = 0.0f
    private val pogo: Pogo = Pogo(context, resolutionPointSize.x, resolutionPointSize.y)


    init {

        this.screenX = resolutionPointSize.x
        this.screenY = resolutionPointSize.y
        screenRatioX = 1920f / screenX
        screenRatioY = 1080f / screenY

        numb_right = Random.nextInt(2,4)
        numb_left = 3 - numb_right
        for (i in 0..numb_right) {
            var counter = Random.nextInt(1,5)
            val rock_r = Rock(resources, counter, screenX, screenY)
            rocks_r[i] = rock_r
        }
        for (i in 0..numb_left) {
            var counter = Random.nextInt(2,5)
            val rock_l = Rock(resources, counter, screenX, screenY)
            rocks_l[i] = rock_l

        }
        background1 = Background(screenX, screenY, resources)
        background2 = Background(screenX, screenY, resources)
        background2.x = screenX.toFloat()
        gameThread.start()
        }
    override fun run() {
        var fps: Long = 0
        while (running) {
            val startFrameTime = System.currentTimeMillis()
            update(fps)
            draw()
            fps = calcFps(fps, startFrameTime)
            sleep()
            }
    }

    private fun update(fps: Long) {

        background1.x = background1.x - 3 * screenRatioX
        background2.x = background2.x - 3 * screenRatioX
        if (background1.x + background1.background.width < 0) {
            background1.x = screenX.toFloat()
        }
        if (background2.x + background2.background.width < 0) {
            background2.x = screenX.toFloat()
        }
        for (i in 0..numb_right) {
            rocks_r[i]!!.x = rocks_r[i]!!.x - rocks_r[i]!!.speed
            if (rocks_r[i]!!.x + rocks_r[i]!!.width < 0) {
                val bound = (7 * screenRatioX).toInt()
                rocks_r[i]!!.speed = Random.nextInt(bound)
                if (rocks_r[i]!!.speed < 2 * screenRatioX) rocks_r[i]!!.speed = (2* screenRatioX).toInt()
                rocks_r[i]!!.x = screenX
                var pos_y = Random.nextInt(screenY - 2*rocks_r[i]!!.height)
                while(!checkAvailable(pos_y)) {
                    pos_y = Random.nextInt(screenY - 2*rocks_r[i]!!.height)
                }
                rocks_r[i]!!.y = pos_y

            }
        }

        for (i in 0..numb_left) {
            rocks_l[i]!!.x = rocks_l[i]!!.x + rocks_l[i]!!.speed
            if (rocks_l[i]!!.x > screenX) {
                val bound = (7 * screenRatioX).toInt()
                rocks_l[i]!!.speed = Random.nextInt(bound)
                if (rocks_l[i]!!.speed < 2 * screenRatioX) rocks_l[i]!!.speed = (2 * screenRatioX).toInt()
                rocks_l[i]!!.x = 0
                var pos_y = Random.nextInt(screenY - 2*rocks_l[i]!!.height)
                while(!checkAvailable(pos_y)) {
                    pos_y = Random.nextInt(screenY - 2*rocks_l[i]!!.height)
                }
                rocks_l[i]!!.y = pos_y
            }
        }

        if (new_x != -1.1f) {
            pogo.position.left = new_x - pogo.width / 2
            pogo.position.top = new_y - pogo.height / 2
        }


    }

    private fun checkAvailable(pos_y: Int): Boolean {
        for ( i in 0.. numb_right) {
            if (kotlin.math.abs(rocks_r[i]!!.y - pos_y) < screenY/12) return false
        }
        for ( i in 0..numb_left) {
            if (kotlin.math.abs(rocks_l[i]!!.y - pos_y) < screenY/12) return false
        }
        return true
    }
    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint)
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint)
            canvas.drawBitmap(pogo.bitmap, pogo.position.left, pogo.position.top, paint)
            for (i in 0..numb_right) canvas.drawBitmap(rocks_r[i]!!.objects, rocks_r[i]!!.x.toFloat(), rocks_r[i]!!.y.toFloat(), paint)
            for (i in 0..numb_left) canvas.drawBitmap(rocks_l[i]!!.objects, rocks_l[i]!!.x.toFloat(), rocks_l[i]!!.y.toFloat(), paint)
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun calcFps(fps: Long, startFrameTime: Long) : Long {
        val timeThisFrame = System.currentTimeMillis() - startFrameTime
        if (timeThisFrame >= 1) {
            return 1000 / timeThisFrame
        }

        return fps
    }
    fun pause() {
        try {
            running = false
            gameThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
    fun resume() {
        running = true
        var thread = Thread(this)
        thread.start()
    }
    private fun sleep() {
        try {
            Thread.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {

         new_x = motionEvent!!.x
         new_y = motionEvent!!.y
     return true
     }

}