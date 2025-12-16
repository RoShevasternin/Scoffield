package com.piriamidone.twispinton.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import com.piriamidone.twispinton.game.actors.masks.Mask
import com.piriamidone.twispinton.game.utils.advanced.AdvancedGroup
import com.piriamidone.twispinton.game.utils.advanced.AdvancedScreen
import com.piriamidone.twispinton.game.utils.runGDX

class ATimer(override val screen: AdvancedScreen): AdvancedGroup() {

    private val progressImage   = Image(screen.game.alllAssets.timer_progress)
    private val mask            = Mask(screen, screen.game.alllAssets.timer_mask, alphaWidth = 1000)

    var isPause = false

    override fun addActorsOnGroup() {
        addMask()
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addAndFillActor(mask)
        mask.addProgressImg()
    }

    private fun AdvancedGroup.addProgressImg() {
        addAndFillActor(progressImage)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun startTimer(finishBlock: () -> Unit) {
        coroutine?.launch {
            var time    = 15f
            var isStart = true

            runGDX { progressImage.addAction(Actions.moveTo(-626f, 0f, time)) }

            while (isActive && time > 0) {
                if (isPause.not()) {
                    delay(1000)
                    --time
//                    val t = (time%60).toInt()
//                    progressLabel.setText("00:${if (t<10) "0$t" else t}")
//                    log("time = $time")
                    if (isStart) {
                        isStart = false
                        runGDX { progressImage.addAction(Actions.moveTo(-626f, 0f, time)) }
                    }
                } else {
                    isStart = true
                    runGDX { progressImage.clearActions() }
                }
            }
            if (time <= 0) {
                cancel()
                finishBlock()
            }
        }
    }


}