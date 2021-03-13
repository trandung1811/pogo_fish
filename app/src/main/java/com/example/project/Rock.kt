package com.example.project

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlin.random.Random

class Rock {
    private lateinit var rock_1: Bitmap
    private lateinit var rock_2: Bitmap
    private lateinit var rock_3: Bitmap
    private lateinit var gameView: GameView
    var width = 0
    var height = 0

    fun createRock(gameView: GameView, screen_x: Int, screen_y: Int, res: Resources) {

        this.gameView = gameView;
        rock_1 = BitmapFactory.decodeResource(res,R.drawable.obstacle1)
        rock_2 = BitmapFactory.decodeResource(res,R.drawable.obstacle2)
        rock_3 = BitmapFactory.decodeResource(res,R.drawable.obstacle3)
        width = screen_x/4
        height = screen_y/10

        rock_1 = Bitmap.createScaledBitmap(rock_1,width, height, false)
        rock_2 = Bitmap.createScaledBitmap(rock_2,width, height, false)
        rock_3 = Bitmap.createScaledBitmap(rock_3,width, height, false)

    }
    fun getRock(rand: Int) : Bitmap {
        when (rand) {
            0 -> return rock_1
            1 -> return rock_2
            2 -> return rock_3
        }
        return rock_1
    }

}