package com.bonanza.sweetcards.game.util

import com.badlogic.gdx.math.Vector2

object Layout {

    object Game {
        val grey = LayoutData(197f, 468f, 123f, 156f, hs = 48f)
        val gold = LayoutData(25f, 468f, 126f, 150f, hs = 900f)
        val g2 = Vector2(111f, 260f)
        val g3 = Vector2(197f, 53f)
        val y  = 53f
    }

    data class LayoutData(
        val x: Float = 0f,
        val y: Float = 0f,
        val w: Float = 0f,
        val h: Float = 0f,
        // horizontal space
        val hs: Float = 0f,
        // vertical space
        val vs: Float = 0f,
    )

}












