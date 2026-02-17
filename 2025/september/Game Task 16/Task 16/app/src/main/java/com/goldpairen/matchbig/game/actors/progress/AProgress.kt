package com.goldpairen.matchbig.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.goldpairen.matchbig.game.actors.shader.AMaskGroup
import com.goldpairen.matchbig.game.utils.advanced.AdvancedGroup
import com.goldpairen.matchbig.game.utils.advanced.AdvancedScreen
import com.goldpairen.matchbig.game.utils.gdxGame
import com.goldpairen.matchbig.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 569f

    private val imgProgress = Image(gdxGame.assetsAll.prog)
    private val imgCursor   = Image(gdxGame.assetsAll.ruchka)
    private val mask        = AMaskGroup(screen, gdxGame.assetsAll.MASK)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x = (percent * onePercentX) - LENGTH
                    imgCursor.x = imgProgress.x + LENGTH - 32
                }
            }
        }

        addListener(inputListener())
    }

    // Actors ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(24f, 16f, 569f, 44f)

        mask.addAndFillActor(imgProgress)
        //mask.debug()

        addActor(imgCursor)
        imgCursor.setBounds(0f, 6f, 65f, 59f)
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

            event?.stop()
        }
    }

}
