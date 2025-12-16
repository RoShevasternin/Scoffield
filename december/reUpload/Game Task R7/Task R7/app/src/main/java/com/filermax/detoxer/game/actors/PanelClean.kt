package com.filermax.detoxer.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.filermax.detoxer.game.utils.*
import com.filermax.detoxer.game.utils.actor.disable
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import com.filermax.detoxer.game.utils.Layout.PanelClean as LPC

class PanelClean(private val type: Type): AdvancedGroup() {

    companion object {
        val sizeMB = (CLEAN_SIZE_MB_MIN..CLEAN_SIZE_MB_MAX).shuffled().first()
    }

    var finishBlock: () -> Unit = {}

    private val sizeMBFlow = MutableStateFlow(sizeMB)

    private val titleText    = "System Cleaning"
    private val progressText = "Cleaning"
    private val param1Text   = "Cleaning the system cache..."
    private val param2Text   = "Delete residual files..."
    private val param3Text   = "Delete unwanted ad files..."
    private val param4Text   = "Delete temporary files..."

    private val title    = Title(titleText)
    private val orb      = Orb(sizeMBFlow, type)
    private val progress = Progress(progressText, param1Text, param2Text, param3Text, param4Text)



    override fun sizeChanged() {
        super.sizeChanged()
        disable()
        if(width > 0 && height > 0) addActorsOnGroup()
    }

    private fun addActorsOnGroup() {
        addOrb()
        addTitle()
        addProgress()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addTitle() {
        addAndFillActor(title)
    }



    private fun addOrb() {
        addActor(orb)
        orb.setBounds(LPC.orb)
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
                    val completerOrb =CompletableDeferred<Unit>()
                    orb.addAction(Actions.sequence(
                        Actions.fadeOut(hideTime),
                        Actions.run { completerOrb.complete(Unit) }
                    ))
                    completerOrb.await()

                    withContext(Dispatchers.Default) {
                        launch { title.showSuccessfully("Cleared $sizeMB mb of trash") }
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
            Actions.run { orb.cleaning() }
        ))
    }

    enum class Type {
        START, FINISH
    }

}