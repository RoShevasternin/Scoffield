package lucky.jogodobicho.fan.game.actors.playable

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import java.util.concurrent.atomic.AtomicBoolean

open class AMovableImage(override val screen: AdvancedScreen, region: TextureRegion): AImage(screen, region) {

    val images     = mutableListOf<AMovableImage>()
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

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                touchDragged(event, x, y, pointer)
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                tmpY = thisY + y - (height/2)
                if (tmpY in 531f..1121f) {
                    this@AMovableImage.y = tmpY

                    when (thisY) {
                        in 1072..1202 -> {
                            is2.set(true)
                            is3.set(true)

                            if (is1.getAndSet(false)) moveFrom(1)
                        }
                        in 826..956   -> {
                            is1.set(true)
                            is3.set(true)

                            if (is2.getAndSet(false)) moveFrom(2)
                        }
                        in 530..660   -> {
                            is1.set(true)
                            is2.set(true)

                            if (is3.getAndSet(false)) moveFrom(3)
                        }
                    }
                }
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                movePos(this@AMovableImage)
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
            1 -> 1121f
            2 -> 826f
            3 -> 531f
            else -> 0f
        }.also { ny ->
            actor.clearActions()
            actor.addAction(Actions.moveTo(startX, ny, 0.2f))
        }
    }

}