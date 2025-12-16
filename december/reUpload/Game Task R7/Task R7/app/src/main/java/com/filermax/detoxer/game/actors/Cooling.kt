package com.filermax.detoxer.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.game.actors.label.ALabelStyle
import com.filermax.detoxer.game.utils.*
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.coroutineContext
import com.filermax.detoxer.game.utils.Layout.Battery as LB
import com.filermax.detoxer.game.utils.Layout.Cooling as LC

class Cooling(
    private val temperatureFlow: MutableStateFlow<Float>,
    private val temperatureMin : Float,
    private val type           : PanelCooling.Type
): AdvancedGroup() {

    private val temperatureGroup  = AdvancedGroup()
    private val temperature1Label = Label("0.0°", ALabelStyle.style(ALabelStyle.Mulish.ExtraBold._45, GameColor.black_15))
    private val temperature2Label = Label("0.0°", ALabelStyle.style(ALabelStyle.Mulish.ExtraBold._67, GameColor.black_40))
    private val temperatureLabel  = Label("0.0°", ALabelStyle.style(ALabelStyle.Mulish.ExtraBold._119, getColorByType()))
    private val temperature3Label = Label("0.0°", ALabelStyle.style(ALabelStyle.Mulish.ExtraBold._67, GameColor.black_40))
    private val temperature4Label = Label("0.0°", ALabelStyle.style(ALabelStyle.Mulish.ExtraBold._45, GameColor.black_15))

    private val listTemperatureLabel = listOf(
        temperature1Label,
        temperature2Label,
        temperatureLabel,
        temperature3Label,
        temperature4Label,
    )

    private val snowflake = Snowflake()


    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }

    private fun addActorsOnGroup() {
        addAndFillActor(temperatureGroup)
        temperatureGroup.addLabels()
        addLightning()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedGroup.addLabels() {
        addActors(listTemperatureLabel)
        listTemperatureLabel.onEachIndexed { index, label ->
            label.apply {
                setBounds(LC.labels[index])
                setAlignment(Align.center)
            }
        }

        val temperature = when(type) {
            PanelCooling.Type.START  -> temperatureFlow.value
            PanelCooling.Type.FINISH -> temperatureMin
        }
        temperature1Label.setText("${String.format("%.1f", temperature + 0.2f)}°".replace(',', '.'))
        temperature2Label.setText("${String.format("%.1f", temperature + 0.1f)}°".replace(',', '.'))
        temperatureLabel.setText("${String.format("%.1f", temperature)}°".replace(',', '.'))
        temperature3Label.setText("${String.format("%.1f", temperature - 0.1f)}°".replace(',', '.'))
        temperature4Label.setText("${String.format("%.1f", temperature - 0.2f)}°".replace(',', '.'))
    }

    private fun addLightning() {
        addActor(snowflake)
        snowflake.apply {
            setBounds(LB.lightning)
            addAction(Actions.alpha(0f))
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun getColorByType() = when(type) {
        PanelCooling.Type.START -> GameColor.red
        PanelCooling.Type.FINISH -> GameColor.blue
    }

    private suspend fun startAnim() = CompletableDeferred<Unit>().also { continuation ->
        val completerMask = CompletableDeferred<Unit>()
        runGDX {
            temperatureGroup.addAction(Actions.sequence(
                Actions.fadeOut(hideTime),
                Actions.run { completerMask.complete(Unit) }
            ))
        }
        completerMask.await()

        runGDX {
            snowflake.apply {
                addAction(Actions.sequence(
                    Actions.fadeIn(showTime),
                    Actions.run {
                        continuation.complete(Unit)
                        startAnim()
                    }
                ))
            }
        }
    }.await()

    private fun finishAnim() {
        snowflake.finishAnim()
    }

    private suspend fun minusTemperature(block: () -> Unit) = CompletableDeferred<Unit>().also { continuation ->
        val step = 0.1f
        while (coroutineContext.isActive && temperatureFlow.value > temperatureMin) {
            temperatureFlow.apply { value = if ((value - step) < temperatureMin) temperatureMin else value - step }
            delay((30..50).shuffled().first().toLong())
        }
        continuation.complete(Unit)
        block()
    }.await()

    fun cleaning() {
        coroutine.launch {
            startAnim()
            minusTemperature { runGDX { finishAnim() } }
        }
    }

}