package com.filermax.detoxer.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
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
import com.filermax.detoxer.game.utils.Layout.Boost as LB

class Boost(
    private val sizeMBFlow: MutableStateFlow<Int>,
    private val type      : PanelBoost.Type
): AdvancedGroup() {

    private val trashText = "Occupied, mb"

    private val maskGroup     = AdvancedGroup()
    private val mask          = Mask(SpriteManager.BoostRegion.MASK.region)
    private val progressImage = Image(SpriteManager.BoostRegion.PROGRESS.region)
    private val sizeMBLabel   = Label(sizeMBFlow.value.toString(), ALabelStyle.style(ALabelStyle.Mulish.ExtraBold._89, getColorByType()))
    private val trashLabel    = Label(trashText, ALabelStyle.style(ALabelStyle.Mulish.Medium._26, getColorByType()))

    private val rocket        = Rocket()


    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }

    private fun addActorsOnGroup() {
        addAndFillActor(maskGroup)
        maskGroup.apply {
            addImage()
            addLabels()
        }
        addRocket()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedGroup.addImage() {
        addAndFillActor(Image(SpriteManager.BoostRegion.PANEL.region))
        addActor(mask)
        mask.apply {
            setBounds(0f, 0f, this@Boost.width, this@Boost.height)

            addAndFillActor(progressImage)

            when(type) {
                PanelBoost.Type.START  -> {
                    val onePercentY      = height / 100f
                    val sizeMBOnePercent = BOOST_SIZE_MB_MAX / 100f
                    val sizeMBPercent    = sizeMBFlow.value / sizeMBOnePercent

                    (onePercentY * sizeMBPercent) - height
                }
                PanelBoost.Type.FINISH -> -height
            }.also { ny -> progressImage.setPosition(0f, ny) }
        }
    }

    private fun AdvancedGroup.addLabels() {
        addActors(sizeMBLabel, trashLabel)
        sizeMBLabel.apply {
            setBounds(LB.countLabel)
            setAlignment(Align.center)
        }
        trashLabel.apply {
            setBounds(LB.countText)
            setAlignment(Align.center)
        }

        coroutine.launch { collectSizeMB() }
    }

    private fun addRocket() {
        addActor(rocket)
        rocket.apply {
            setBounds(0f, 0f, this@Boost.width, this@Boost.height)
            addAction(Actions.alpha(0f))
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun getColorByType() = when(type) {
        PanelBoost.Type.START -> Color.WHITE
        PanelBoost.Type.FINISH -> Color.BLACK
    }

    private suspend fun collectSizeMB() {
        sizeMBFlow.collect { count ->
            (if (type == PanelBoost.Type.FINISH) 0 else count).also { text -> runGDX { sizeMBLabel.setText(text) } }
        }
    }

    private suspend fun startAnim() = CompletableDeferred<Unit>().also { continuation ->
        val completerMask = CompletableDeferred<Unit>()
        runGDX {
            maskGroup.addAction(Actions.sequence(
                Actions.fadeOut(hideTime),
                Actions.run { completerMask.complete(Unit) }
            ))
        }
        completerMask.await()

        runGDX {
            rocket.apply {
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
        rocket.finishAnim()
    }

    private suspend fun minusSizeMB(block: () -> Unit) = CompletableDeferred<Unit>().also { continuation ->
        var step: Int
        while (coroutineContext.isActive && sizeMBFlow.value > 0) {
            step = (1..10).shuffled().first()
            sizeMBFlow.apply { value = if ((value - step) < 0) 0 else value - step }
            delay((10..15).shuffled().first().toLong())
        }
        continuation.complete(Unit)
        block()
    }.await()

    fun cleaning() {
        coroutine.launch {
            startAnim()
            minusSizeMB { runGDX { finishAnim() } }
        }
    }

}