package com.icertif.pyrzzle.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.icertif.pyrzzle.game.actors.masks.Mask
import com.icertif.pyrzzle.game.utils.advanced.AdvancedGroup
import com.icertif.pyrzzle.game.utils.advanced.AdvancedScreen
import com.icertif.pyrzzle.game.utils.runGDX

class ASettingProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 516f

    private val progressImg = Image(screen.game.allAssets.musicality)
    private val cricketImg  = Image(screen.game.allAssets.four_rect)
    private val mask        = Mask(screen, screen.game.allAssets.miskagu, alphaWidth = 1005)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()
        addCricket()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    cricketImg.x = percent * onePercentX
                    progressImg.x = cricketImg.x - LENGTH
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
        mask.setBounds(0f, 22f, 588f, 38f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addActor(progressImg)
        progressImg.setBounds(-LENGTH, 0f, 588f, 38f)
    }

    private fun AdvancedGroup.addCricket() {
        addActor(cricketImg)
        cricketImg.setBounds(0f, 0f, 83f, 83f)
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