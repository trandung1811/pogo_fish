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

    private var obstacle: Rock = Rock()
    private var screenX = 0
    private var screenY = 0
    private val background1: Background
    private val background2: Background
    companion object {
        var screenRatioX: Float = 0f
        var screenRatioY: Float = 0f
    }
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


        background1.x = background1.x - 4* screenRatioX
        background2.x = background2.x - 4* screenRatioX
        if (background1.x + background1.background.width < 0) {
            background1.x = screenX.toFloat()
        }
        if (background2.x + background2.background.width < 0) {
            background2.x = screenX.toFloat()
        }
        if (new_x != -1.1f) {
            pogo.position.left = new_x - pogo.width/2
            pogo.position.top = new_y - pogo.height/2
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint)
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint)

            canvas.drawBitmap(pogo.bitmap, pogo.position.left, pogo.position.top, paint)

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
            Thread.sleep(2)
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