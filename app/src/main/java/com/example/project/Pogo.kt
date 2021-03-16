package com.example.project

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Pogo(context: Context,
           private var screenX: Int,
           private var screenY: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.image)

    val width: Float = screenX / 7.0f
    val height: Float = screenY / 9.0f
    var screenRatioX = 1920f / screenX
    val position: RectF = RectF(
            (screenX/2).toFloat(),
            screenY - height - 300.0f,
        screenX / 2.0f + width,
        screenY.toFloat() - 100.0f
    )

    var acceleration: Float = 0.0f
    var velocity: Float = 0.0f
    var angleRad: Float = 0.0f

    init {
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(), false)
    }
}