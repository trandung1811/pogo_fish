package com.example.project

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect


class Rock (res: Resources, counter: Int, screenX: Int, screenY: Int) {
    var speed = 10
    var x = 0
    var y: Int
    var width: Int
    var height: Int
    val counter = counter
    var object1: Bitmap
    var object2: Bitmap
    var object3: Bitmap
    val objects: Bitmap
        get() {
            if (counter == 1) {
                return object1
            }
            if (counter == 2) {
                return object2
            }
            if (counter == 3) {
                return object3
            }
            return object3
        }

    init {


        object1 = BitmapFactory.decodeResource(res, R.drawable.obstacle1)
        object2 = BitmapFactory.decodeResource(res, R.drawable.obstacle2)
        object3 = BitmapFactory.decodeResource(res, R.drawable.obstacle3)

        width = screenX/7
        height = screenY/12

        object1 = Bitmap.createScaledBitmap(object1, width, height, false)
        object2 = Bitmap.createScaledBitmap(object2, width, height, false)
        object3 = Bitmap.createScaledBitmap(object3, width, height, false)
        y = -2*height
    }
}
