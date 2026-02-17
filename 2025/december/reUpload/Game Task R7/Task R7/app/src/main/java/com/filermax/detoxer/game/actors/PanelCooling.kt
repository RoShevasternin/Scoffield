package com.filermax.detoxer.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.filermax.detoxer.game.utils.*
import com.filermax.detoxer.game.utils.actor.disable
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class PanelCooling(private val type: Type): AdvancedGroup() {

    companion object {
        val temperatureStart = (COOLING_TEMPERATURE_MAX_MIN..COOLING_TEMPERATURE_MAX).shuffled().first().toFloat()
        val temperatureEnd   = (COOLING_TEMPERATURE_MIN..COOLING_TEMPERATURE_MIN_MAX).shuffled().first().toFloat()
    }

    var finishBlock: () -> Unit = {}

    private val temperatureFlow = MutableStateFlow(temperatureStart)

    private val titleText    = "CPU Cooling"
    private val progressText = "Cooling"
    private val param1Text   = "Phone memory analysis..."
    private val param2Text   = "Performance optimization..."
    private val param3Text   = "Stop background apps..."
    private val param4Text   = "Free RAM..."

    private val title    = Title(titleText)
    private val cooling  = Cooling(temperatureFlow, temperatureEnd, type)
    private val progress = Progress(progressText, param1Text, param2Text, param3Text, param4Text)



    override fun sizeChanged() {
        super.sizeChanged()
        disable()
        if(width > 0 && height > 0) addActorsOnGroup()
    }

    private fun addActorsOnGroup() {
        addCooling()
        addTitle()
        addProgress()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addTitle() {
        addAndFillActor(title)
    }

    private fun addCooling() {
        addAndFillActor(cooling)
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
            val temperatureOnePercent = (temperatureStart - temperatureEnd) / 100f

            temperatureFlow.collect { temperature ->
                progress.percentFlow.value = ((temperatureStart - temperature) / temperatureOnePercent).toInt()
            }
        }
    }

    private fun collectProgress() {
        coroutine.launch {
            progress.percentFlow.collect { percent ->
                if (percent >= 99) {
                    withContext(Dispatchers.Default) {
                        launch { title.showSuccessfully("Cooled") }
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
            Actions.run { cooling.cleaning() }
        ))
    }

    enum class Type {
        START, FINISH
    }

}