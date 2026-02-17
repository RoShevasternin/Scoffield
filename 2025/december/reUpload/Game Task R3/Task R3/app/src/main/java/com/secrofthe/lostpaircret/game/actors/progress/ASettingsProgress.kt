package com.secrofthe.lostpaircret.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.secrofthe.lostpaircret.game.actors.masks.Mask
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedGroup
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedScreen
import com.secrofthe.lostpaircret.game.utils.runGDX

class ASettingsProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 429f

    private val progressImg = Image(screen.game.allAssets.porfix)
    private val cricketImg  = Image(screen.game.allAssets.criket)
    private val mask        = Mask(screen, screen.game.allAssets.porfix_mask, alphaWidth = 1100)

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
        mask.setBounds(0f, 38f, 530f, 40f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addActor(progressImg)
        progressImg.setBounds(-530f, 0f, 530f, 40f)
    }

    private fun AdvancedGroup.addCricket() {
        addActor(cricketImg)
        cricketImg.setBounds(0f, 0f, 107f, 110f)
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