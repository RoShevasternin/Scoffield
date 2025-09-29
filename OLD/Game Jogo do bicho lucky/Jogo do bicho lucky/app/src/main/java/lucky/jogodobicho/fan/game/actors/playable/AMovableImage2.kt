package lucky.jogodobicho.fan.game.actors.playable

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import java.util.concurrent.atomic.AtomicBoolean

open class AMovableImage2(override val screen: AdvancedScreen, region: TextureRegion): AImage(screen, region) {

    val images     = mutableListOf<AMovableImage2>()
    var id         = -1
    var positionID = -1

    private val thisY: Int get() = y.toInt()
    private var startX = x
    private var startY = y
    private var tmpPos = -1

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        startX = x
        startY = y
        images.remove(this)

        addListener(object : InputListener() {
            var tmpY = 0f

            val is1 = AtomicBoolean(true)
            val is2 = AtomicBoolean(true)
            val is3 = AtomicBoolean(true)
            val is4 = AtomicBoolean(true)

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                touchDragged(event, x, y, pointer)
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                tmpY = thisY + y - (height/2)
                if (tmpY in 519f..1179f) {
                    this@AMovableImage2.y = tmpY

                    when (thisY) {
                        in 1115..1191 -> {
                            is2.set(true)
                            is3.set(true)
                            is4.set(true)

                            if (is1.getAndSet(false)) moveFrom(1)
                        }
                        in 959..1035   -> {
                            is1.set(true)
                            is3.set(true)
                            is4.set(true)

                            if (is2.getAndSet(false)) moveFrom(2)
                        }
                        in 739..815   -> {
                            is1.set(true)
                            is2.set(true)
                            is4.set(true)

                            if (is3.getAndSet(false)) moveFrom(3)
                        }
                        in 517..593   -> {
                            is1.set(true)
                            is2.set(true)
                            is3.set(true)

                            if (is4.getAndSet(false)) moveFrom(4)
                        }
                    }
                }
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                movePos(this@AMovableImage2)
            }
        })
    }

    private fun moveFrom(pos: Int) {
        images.onEach {
            if (it.positionID == pos) {
                movePos(it)

                tmpPos        = positionID
                positionID    = pos
                it.positionID = tmpPos
            }
        }
    }

    private fun movePos(actor: Actor) {
        when(positionID) {
            1 -> 1179f
            2 -> 959f
            3 -> 739f
            4 -> 517f
            else -> 0f
        }.also { ny ->
            actor.clearActions()
            actor.addAction(Actions.moveTo(startX, ny, 0.2f))
        }
    }

}