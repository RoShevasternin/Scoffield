package lucky.jogodobicho.fan.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.game.actors.masks.Mask
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.runGDX

class AVolumeRegulator(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 599f

    private val backgroundImage = Image(screen.game.gameAssets.FLATTER_BACKGROUND)
    private val progressImage   = Image(screen.game.gameAssets.FLATTER_LOADINGER)
    private val ellipsImage     = Image(screen.game.gameAssets.FLATTER_ELIPSE)
    private val maska           = Mask(screen, screen.game.gameAssets.FLATTER_BLACK, alphaWidth = 900)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addBackground()
        addMaskar()
        addEllipse()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.x = percent * onePercentX - LENGTH
                    ellipsImage.x   = (progressImage.x + LENGTH) - (ellipsImage.width/2)
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addBackground() {
        addActor(backgroundImage)
        backgroundImage.setBounds(0f, 11.5f, LENGTH, 56f)
    }

    private fun AdvancedGroup.addMaskar() {
        addActor(maska)
        maska.setBounds(0f, 11.5f, LENGTH, 56f)
        maska.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addActor(progressImage)
        progressImage.setBounds(-LENGTH, 0f, LENGTH, 56f)
    }

    private fun AdvancedGroup.addEllipse() {
        addActor(ellipsImage)
        ellipsImage.setBounds(0f, 0f, 53f, 77f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun inputListener() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            progressPercentFlow.value = when {
                x <= 0 -> 0f
                x >= LENGTH -> 100f
                else -> x / onePercentX
            }
        }
    }

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}