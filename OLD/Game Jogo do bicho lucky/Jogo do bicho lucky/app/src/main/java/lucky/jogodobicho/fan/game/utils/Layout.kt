package lucky.jogodobicho.fan.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val flatter    = LayoutData(148f,915f,784f,88f)
        val flatterLbl = LayoutData(421f,938f,239f,39f)
    }

    object Background {
        val babochke = LayoutData(471f, 1408f, 276f,277f)
        val cock     = LayoutData(836f, 642f, 260f,562f)
        val monkey   = LayoutData(109f, 935f, 262f,242f)
        val ass      = LayoutData(-26f, 0f, 799f,898f)
        val box      = LayoutData(699f, 0f, 381f,390f)
        val grass    = LayoutData(-3f, 0f, 1083f,351f)
    }

    object General {
        val three_points = LayoutData(63f, 1738f, 141f, 141f)
        val six_teeth    = LayoutData(876f, 1738f, 141f, 141f)
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












