package com.piriamidone.twispinton.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val phara   = LayoutData(139f,26f,802f,1420f)
        val loading = LayoutData(61f,1564f,969f,63f)
        val progres = LayoutData(44f,1551f,992f,91f)
        val loaring = LayoutData(325f,1658f,407f,106f)
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












