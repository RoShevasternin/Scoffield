package lucky.jogodobicho.fan.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.game.actors.masks.Mask
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.runGDX

class AFlatter(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 738f

    private val backgroundImage = Image(screen.game.splashAssets.karas)
    private val progressImage   = Image(screen.game.splashAssets.filatov)
    private val mask            = Mask(screen, screen.game.splashAssets.IRON_MAN, alphaWidth = 999)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    private val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addBrackout()
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX { progressImage.x = percent * onePercentX - LENGTH }
            }
        }

//        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addBrackout() {
        addAndFillActor(backgroundImage)
//        backgroundImage.setBounds(0f, 28.8f, 992f, 93.8f)
    }

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(22f, 31f, 738f, 32f)
        mask.addLoading()
    }

    private fun AdvancedGroup.addLoading() {
        addActor(progressImage)
        progressImage.setBounds(-LENGTH, 0f, LENGTH, 32f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

//    private fun inputListener() = object : InputListener() {
//        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
//            touchDragged(event, x, y, pointer)
//            return true
//        }
//
//        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
//            progressPercentFlow.value = when {
//                x <= 0 -> 0f
//                x >= LENGTH -> 100f
//                else -> x / onePercentX
//            }
//        }
//    }

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}