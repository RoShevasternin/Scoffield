package com.filermax.detoxer.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val logo   = LayoutData(100f,710f,499f,93f)
        val loader = LayoutData(313f,348f,75f,75f)
    }

    object Common {
        val screen = LayoutData(0f,0f, 700f,1400f)

        val description  = LayoutData(25f,560f,650f,111f)
        val title        = LayoutData(37f,691f,626f,40f)
        val button       = LayoutData(117f,300f,466f,93f)
        val controlPanel = LayoutData(0f,62f,700f,145f)
        val back         = LayoutData(37f, 1335f, 625f, 45f)

    }

    object ControlPanel {
        val separator = LayoutData(0f, 142f, 700f, 3f)
        val checkBox  = LayoutData(0f, 30f, 175f, 84f, hs = 0f)
    }

    object PanelClean {
        val orb  = LayoutData(14f, 643f, 673f, 673f)
    }

    object PanelBoost {
        val boost = LayoutData(132f, 745f, 467f, 467f)
    }

    object Orb {
        val countLabel = LayoutData(170f, 300f, 333f, 112f)
        val countText  = LayoutData(170f, 258f, 333f, 31f)
    }

    object Boost {
        val countLabel = LayoutData(75f, 198f, 315f, 112f)
        val countText  = LayoutData(75f, 156f, 315f, 31f)
    }

    object Battery {
        val countLabel = LayoutData(32f, 826f, 637f, 112f)
        val countText  = LayoutData(32f, 783f, 637f, 31f)

        val battery   = LayoutData(118f, 961f, 459f, 214f)
        val mask      = LayoutData(157f, 1000f, 341f, 138f)
        val lightning = LayoutData(119f, 747f, 476f, 461f)
    }

    object Cooling {
        val labels = listOf(
            LayoutData(136f, 1157f, 428f, 56f),
            LayoutData(136f, 1073f, 428f, 84f),
            LayoutData(136f, 923f, 428f, 150f),
            LayoutData(136f, 840f, 428f, 84f),
            LayoutData(136f, 784f, 428f, 56f),
        )
    }

    object Title {
        val title       = LayoutData(14f,1287f,673f,56f)
        val titleEndY   = 1208f
        val description = LayoutData(14f,1156f,673f,42f)
    }

    object Progress {
        val title  = LayoutData(14f,615f,673f,56f)
        val group  = LayoutData(37f,305f,625f,235f)
        val groupY = 845f
        val param  = LayoutData(0f,190f,625f,45f, vs = 19f)
        val status = LayoutData(580f,190f,45f,45f, vs = 19f)
    }

    object Back {
        val title = LayoutData(63f, 4f, 562f, 36f)
        val image = LayoutData(0f, 0f, 45f,45f)
    }

    object Rocket {
        val star1 = LayoutData(114f, 65f, 46f, 46f)
        val star2 = LayoutData(43f, 90f, 46f, 46f)
        val star3 = LayoutData(0f, 0f, 93f, 93f)
    }

    object Lightning {
        val lightning = LayoutData(127f, 45f, 206f, 373f)
    }

    object Snowflake {
        val snowflake = LayoutData(88f, 94f, 283f, 276f)
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
    ) {

        fun position() = Vector2(x, y)
        fun size() = Size(w, h)

    }

}












