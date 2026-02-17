package com.plinko.ballwinx100.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val play = LayoutData(293f,178f,504f,253f)
        val policy = LayoutData(303f,20f,474f,100f)
    }

    object Lvls {
        val lvl1 = LayoutData(352f,64f,144f,144f)
        val lvl2 = LayoutData(534f,185f,144f,144f)
        val lvl3 = LayoutData(426f,420f,144f,144f)
        val lvl4 = LayoutData(505f,616f,144f,144f)
        val lvl5 = LayoutData(503f,825f,144f,144f)
        val lvl6 = LayoutData(471f,1038f,144f,144f)
        val lvl7 = LayoutData(561f,1279f,144f,144f)
        val lvl8 = LayoutData(440f,1488f,144f,144f)
    }

    object Games {
        val ids = listOf(
            Vector2(114f, 87f),
            Vector2(409f, 87f),
            Vector2(704f, 87f),
            Vector2(999f, 87f),
        )
        val ids2 = listOf(
            Vector2(101f, 317f),
            Vector2(421f, 317f),
            Vector2(741f, 317f),
            Vector2(101f, 67f),
            Vector2(421f, 67f),
            Vector2(741f, 67f),
        )
        val ctnrs1 = listOf(
            Vector2(52f, 768f),
            Vector2(394f, 768f),
            Vector2(736f, 768f),
        )
        val ctnrs2 = listOf(
            Vector2(63f, 963f),
            Vector2(405f, 963f),
            Vector2(747f, 963f),
            Vector2(63f, 549f),
            Vector2(405f, 549f),
            Vector2(747f, 549f),
        )
        val movable = listOf(
            Vector2(132f, 242f),
            Vector2(427f, 242f),
            Vector2(722f, 242f),
        )
        val movable2 = listOf(
            Vector2(130f, 272f),
            Vector2(450f, 272f),
            Vector2(770f, 272f),
            Vector2(130f, 42f),
            Vector2(450f, 42f),
            Vector2(770f, 42f),
        )
        val yesimg = listOf(
            Vector2(86f, 852f),
            Vector2(428f, 852f),
            Vector2(770f, 852f),
        )
        val yesimg2 = listOf(
            Vector2(109f, 1051f),
            Vector2(451f, 1051f),
            Vector2(793f, 1051f),
            Vector2(109f, 637f),
            Vector2(451f, 637f),
            Vector2(793f, 637f),
        )
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












