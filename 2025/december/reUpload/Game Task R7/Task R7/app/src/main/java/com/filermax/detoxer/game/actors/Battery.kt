package com.filermax.detoxer.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.game.actors.label.ALabelStyle
import com.filermax.detoxer.game.actors.masks.normal.Mask
import com.filermax.detoxer.game.manager.SpriteManager
import com.filermax.detoxer.game.utils.*
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.coroutineContext
import com.filermax.detoxer.game.utils.Layout.Battery as LB

class Battery(
    private val minutesFlow: MutableStateFlow<Int>,
    private val minutesMax : Int,
    private val type       : PanelBattery.Type
): AdvancedGroup() {

    private val operatingText = "Operating time"

    private val batteryGroup   = AdvancedGroup()
    private val frameImage     = Image(SpriteManager.BatteryRegion.FRAME.region)
    private val mask           = Mask(SpriteManager.BatteryRegion.MASK.region)
    private val progressImage  = Image()
    private val minutesLabel   = Label("0 h 0 min", ALabelStyle.style(ALabelStyle.Mulish.ExtraBold._89, Color.BLACK))
    private val operatingLabel = Label(operatingText, ALabelStyle.style(ALabelStyle.Mulish.Medium._26, Color.BLACK))

    private val lightning = Lightning()


    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }

    private fun addActorsOnGroup() {
        addAndFillActor(batteryGroup)
        batteryGroup.apply {
            addBattery()
            addLabels()
        }
        addLightning()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedGroup.addBattery() {
        addActors(frameImage, mask)

        frameImage.setBounds(LB.battery)

        mask.apply {
            setBounds(LB.mask)
            addAndFillActor(progressImage)

            val onePercentX       = width / 100f
            val minutesOnePercent = BATTERY_MINUTES_MAX_MAX / 100f
            var minutesPercent    = 0f

            progressImage.drawable = when(type) {
                PanelBattery.Type.START  -> {
                    minutesPercent = minutesFlow.value / minutesOnePercent
                    TextureRegionDrawable(SpriteManager.BatteryRegion.PROGRESS_ORANGE.region)
                }
                PanelBattery.Type.FINISH -> {
                    minutesPercent = minutesMax / minutesOnePercent
                    TextureRegionDrawable(SpriteManager.BatteryRegion.PROGRESS_BLUE.region)
                }
            }
            progressImage.setPosition((onePercentX * minutesPercent) - width, 0f)
        }
    }

    private fun AdvancedGroup.addLabels() {
        addActors(minutesLabel, operatingLabel)
        minutesLabel.apply {
            setBounds(LB.countLabel)
            setAlignment(Align.center)
        }
        operatingLabel.apply {
            setBounds(LB.countText)
            setAlignment(Align.center)
        }

        (if (type == PanelBattery.Type.FINISH) minutesMax else minutesFlow.value).also { time ->
            val hours   = time / 60
            val minutes = time % 60
            minutesLabel.setText("$hours h $minutes min")
        }
    }

    private fun addLightning() {
        addActor(lightning)
        lightning.apply {
            setBounds(LB.lightning)
            addAction(Actions.alpha(0f))
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private suspend fun startAnim() = CompletableDeferred<Unit>().also { continuation ->
        val completerMask = CompletableDeferred<Unit>()
        runGDX {
            batteryGroup.addAction(Actions.sequence(
                Actions.fadeOut(hideTime),
                Actions.run { completerMask.complete(Unit) }
            ))
        }
        completerMask.await()

        runGDX {
            lightning.apply {
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
        lightning.finishAnim()
    }

    private suspend fun plusMinutes(block: () -> Unit) = CompletableDeferred<Unit>().also { continuation ->
        var step: Int
        while (coroutineContext.isActive && minutesFlow.value < minutesMax) {
            step = (1..3).shuffled().first()
            minutesFlow.apply { value = if ((value + step) > minutesMax) minutesMax else value + step }
            delay((10..45).shuffled().first().toLong())
        }
        continuation.complete(Unit)
        block()
    }.await()

    fun cleaning() {
        coroutine.launch {
            startAnim()
            plusMinutes { runGDX { finishAnim() } }
        }
    }

}