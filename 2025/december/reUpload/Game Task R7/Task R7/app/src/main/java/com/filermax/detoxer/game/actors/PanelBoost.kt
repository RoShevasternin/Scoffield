package com.filermax.detoxer.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.filermax.detoxer.game.utils.*
import com.filermax.detoxer.game.utils.actor.disable
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import com.filermax.detoxer.game.utils.Layout.PanelBoost as LPB

class PanelBoost(private val type: Type): AdvancedGroup() {

    companion object {
        val sizeMB = (BOOST_SIZE_MB_MIN..BOOST_SIZE_MB_MAX).shuffled().first()
    }

    var finishBlock: () -> Unit = {}

    private val sizeMBFlow = MutableStateFlow(sizeMB)

    private val titleText    = "Boost the system"
    private val progressText = "Boost"
    private val param1Text   = "Analyzing phone memory..."
    private val param2Text   = "Optimize performance..."
    private val param3Text   = "Stop background apps..."
    private val param4Text   = "Free up RAM..."

    private val title    = Title(titleText)
    private val boost    = Boost(sizeMBFlow, type)
    private val progress = Progress(progressText, param1Text, param2Text, param3Text, param4Text)



    override fun sizeChanged() {
        super.sizeChanged()
        disable()
        if(width > 0 && height > 0) addActorsOnGroup()
    }

    private fun addActorsOnGroup() {
        addBoost()
        addTitle()
        addProgress()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addTitle() {
        addAndFillActor(title)
    }

    private fun addBoost() {
        addActor(boost)
        boost.setBounds(LPB.boost)
    }

    private fun addProgress() {
        addAndFillActor(progress)
        progress.addAction(Actions.alpha(0f))
        collectSizeMBForProgress()
        collectProgress()
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun collectSizeMBForProgress() {
        coroutine.launch {
            val sizeMBOnePercent = sizeMB / 100f

            sizeMBFlow.collect { size -> progress.percentFlow.value = 100 - (size / sizeMBOnePercent).toInt()  }
        }
    }

    private fun collectProgress() {
        coroutine.launch {
            progress.percentFlow.collect { percent ->
                if (percent >= 99) {
                    withContext(Dispatchers.Default) {
                        launch { title.showSuccessfully("Cleared $sizeMB mb") }
                        launch { progress.showDone() }
                    }

                    finishBlock()
                }
            }
        }
    }

    fun cleaning() {
        progress.addAction(Actions.sequence(
            Actions.fadeIn(showTime),
            Actions.run { boost.cleaning() }
        ))
    }

    enum class Type {
        START, FINISH
    }

}