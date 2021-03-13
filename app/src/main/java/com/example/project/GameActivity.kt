package com.example.project

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit  var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        gameView = GameView(this, point)
        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }
    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

}