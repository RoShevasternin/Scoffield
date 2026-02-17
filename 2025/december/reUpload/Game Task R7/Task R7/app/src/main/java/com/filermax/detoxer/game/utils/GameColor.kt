package com.filermax.detoxer.game.utils

import com.badlogic.gdx.graphics.Color

object GameColor {

    val black_15 = rgba(0f, 0f, 0f, 0.15f)
    val black_40 = rgba(0f, 0f, 0f, 0.4f)
    val black_60 = rgba(0f, 0f, 0f, 0.6f)

    val blue = rgba(0f, 65f, 255f, 1f)
    val red  = rgba(255f, 60f, 0f, 1f)


    fun rgba(r: Float, g: Float, b: Float, a: Float = 1f) = Color(r / 255f, g / 255f, b / 255f, a)

}