package com.relict.arthunt.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relict.arthunt.game.actors.masks.Mask
import com.relict.arthunt.game.utils.advanced.AdvancedGroup
import com.relict.arthunt.game.utils.advanced.AdvancedScreen
import com.relict.arthunt.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgressValue(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 563f

    private val progressImg = Image(screen.game.allAssets.porogress)
    private val scrollerImg = Image(screen.game.allAssets.hand)
    private val mask        = Mask(screen, screen.game.allAssets.MASKA_FOR_PROGRESS, alphaWidth = 800)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()
        addScroller()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    scrollerImg.x = percent * onePercentX
                    progressImg.x = scrollerImg.x - LENGTH
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(0f, 10f, 604f, 49f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addActor(progressImg)
        progressImg.setBounds(-604f, 0f, 604f, 49f)
    }

    private fun AdvancedGroup.addScroller() {
        addActor(scrollerImg)
        scrollerImg.setBounds(0f, 0f, 70f, 70f)
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