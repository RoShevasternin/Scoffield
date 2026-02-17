package com.icertif.pyrzzle.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val loading = LayoutData(124f,1005f,829f,85f)
        val texteri = LayoutData(466f,986f,148f,124f)
    }

    data class LayoutData(
        var x: Float = 0f,
        var y: Float = 0f,
        var w: Float = 0f,
        var h: Float = 0f,
        // horizontal space
        val hs: Float = 0f,
        // vertical space
        val vs: Float = 0f,
    ) {

        fun position() = Vector2(x, y)
        fun size() = Vector2(w, h)

    }

}












