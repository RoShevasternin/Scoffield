package com.icertif.pyrzzle.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.icertif.pyrzzle.game.actors.masks.Mask
import com.icertif.pyrzzle.game.utils.advanced.AdvancedGroup
import com.icertif.pyrzzle.game.utils.advanced.AdvancedScreen
import com.icertif.pyrzzle.game.utils.runGDX

class ALoading(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 829f

    private val progressImage = Image(screen.game.loaderAssets.loader)
    private val mask          = Mask(screen, screen.game.loaderAssets.maska, alphaWidth = 940)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
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

    private fun AdvancedGroup.addMask() {
        addAndFillActor(mask)
        mask.addLoading()
    }

    private fun AdvancedGroup.addLoading() {
        addActor(progressImage)
        progressImage.setBounds(-LENGTH, 0f, LENGTH, 85f)
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