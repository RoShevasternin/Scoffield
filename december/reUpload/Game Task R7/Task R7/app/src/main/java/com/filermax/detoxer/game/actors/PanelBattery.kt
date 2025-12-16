package com.filermax.detoxer.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.filermax.detoxer.game.utils.*
import com.filermax.detoxer.game.utils.actor.disable
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class PanelBattery(private val type: Type): AdvancedGroup() {

    companion object {
        val minutesStart = (BATTERY_MINUTES_MIN..BATTERY_MINUTES_MIN_MAX).shuffled().first()
        val minutesEnd   = (BATTERY_MINUTES_MAX..BATTERY_MINUTES_MAX_MAX).shuffled().first()
    }

    var finishBlock: () -> Unit = {}

    private val minutesFlow = MutableStateFlow(minutesStart)

    private val titleText    = "Power Supply"
    private val progressText = "Checking"
    private val param1Text   = "Battery usage analysis..."
    private val param2Text   = "Disabling background applications..."
    private val param3Text   = "Decreasing brightness..."
    private val param4Text   = "Optimizing battery..."

    private val title    = Title(titleText)
    private val battery  = Battery(minutesFlow, minutesEnd, type)
    private val progress = Progress(progressText, param1Text, param2Text, param3Text, param4Text)



    override fun sizeChanged() {
        super.sizeChanged()
        disable()
        if(width > 0 && height > 0) addActorsOnGroup()
    }

    private fun addActorsOnGroup() {
        addBattery()
        addTitle()
        addProgress()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addTitle() {
        addAndFillActor(title)
    }

    private fun addBattery() {
        addAndFillActor(battery)
    }

    private fun addProgress() {
        addAndFillActor(progress)
        progress.addAction(Actions.alpha(0f))
        collectMinutesForProgress()
        collectProgress()
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun collectMinutesForProgress() {
        coroutine.launch {
            val minutesOnePercent = (minutesEnd - minutesStart) / 100f

            minutesFlow.collect { minutes ->
                ((minutes - minutesStart) / minutesOnePercent).toInt().also { percent ->
                    progress.percentFlow.value = if (percent == 99) 100 else percent
                }
            }
        }
    }

    private fun collectProgress() {
        coroutine.launch {
            progress.percentFlow.collect { percent ->
                if (percent >= 99) {
                    withContext(Dispatchers.Default) {
                        launch { title.showSuccessfully("Operating time is increased") }
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
            Actions.run { battery.cleaning() }
        ))
    }

    enum class Type {
        START, FINISH
    }

}