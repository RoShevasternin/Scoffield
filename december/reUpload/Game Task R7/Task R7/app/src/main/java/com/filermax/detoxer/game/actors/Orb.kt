package com.filermax.detoxer.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.game.actors.label.ALabelStyle
import com.filermax.detoxer.game.manager.SpriteManager
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import com.filermax.detoxer.game.utils.runGDX
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.coroutineContext
import com.filermax.detoxer.game.utils.Layout.Orb as LO

class Orb(
    private val sizeMBFlow: MutableStateFlow<Int>,
    private val type      : PanelClean.Type
): AdvancedGroup() {

    private val trashText = "Trash size, mb"

    private val listSide    = listOf<Side>(Left, Right, Top)

    private val image       = Image()
    private val sizeMBLabel = Label(sizeMBFlow.value.toString(), ALabelStyle.style(ALabelStyle.Mulish.ExtraBold._89, Color.BLACK))
    private val trashLabel  = Label(trashText, ALabelStyle.style(ALabelStyle.Mulish.Medium._26, Color.BLACK))
    private val orbList     = List(50) { Image() }



    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }

    private fun addActorsOnGroup() {
        addOrbList()
        addImage()
        addLabels()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addImage() {
        addActor(image)
        image.apply {
            setBounds(0f, 0f, this@Orb.width, this@Orb.height)
            setOrigin(Align.center)
            when(type) {
                PanelClean.Type.START  -> SpriteManager.CleanRegion.ORB_DEF.region
                PanelClean.Type.FINISH -> SpriteManager.CleanRegion.ORB_FINISH.region
            }.also { region -> drawable = TextureRegionDrawable(region) }
        }
    }

    private fun addLabels() {
        addActors(sizeMBLabel, trashLabel)
        sizeMBLabel.apply {
            setBounds(LO.countLabel)
            setAlignment(Align.center)
        }
        trashLabel.apply {
            setBounds(LO.countText)
            setAlignment(Align.center)
        }

        coroutine.launch { collectSizeMB() }
    }

    private fun addOrbList() {
        val listRegion = listOf<TextureRegion>(
            SpriteManager.CleanRegion.ORB_LIGHT.region,
            SpriteManager.CleanRegion.ORB_DARK.region,
        )

        orbList.onEach { orb ->
            addActor(orb)

            val size = (15..40).shuffled().first().toFloat()
            val x    = (width / 2f) - (size / 2f)
            val y    = (height / 2f) - (size / 2f)

            orb.apply {
                drawable = TextureRegionDrawable(listRegion.shuffled().first())
                setBounds(x, y, size, size)
                addAction(Actions.alpha(0f))
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private suspend fun collectSizeMB() {
        sizeMBFlow.collect { count ->
            (if (type == PanelClean.Type.FINISH) 0 else count).also { text -> runGDX { sizeMBLabel.setText(text) } }
        }
    }

    private suspend fun orbListAnim() {
        coroutineScope { orbList.onEach { orb ->
            val orbX = orb.x
            val orbY = orb.y

             launch {
                MutableStateFlow(0).also { flow -> flow.collect {
                    if (isActive) runGDX {
                        val pos = listSide.shuffled().first().position
                        orb.addAction(Actions.sequence(
                            Actions.parallel(
                                Actions.fadeIn(1f),
                                Actions.moveTo(pos.x, pos.y, 2f),
                            ),
                            Actions.parallel(
                                Actions.alpha(0f),
                                Actions.moveTo(orbX, orbY),
                            ),
                            Actions.run { flow.value++ }
                        ))
                    }
                } }
            }
            delay((100..300).shuffled().first().toLong())
        } }
    }

    private fun imageStartAnim() {
        image.apply {
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.1f, -0.1f, 0.3f),
                Actions.scaleBy(0.1f, 0.1f, 0.3f),
            )))
            drawable = TextureRegionDrawable(SpriteManager.CleanRegion.ORB_START.region)
        }
    }

    private fun imageFinishAnim() {
        image.apply {
            clearActions()
            addAction(Actions.sequence(
                Actions.parallel(
                    Actions.fadeOut(0.3f),
                    Actions.scaleTo(1f, 1f, 0.3f),
                ),
                Actions.run { drawable = TextureRegionDrawable(SpriteManager.CleanRegion.ORB_FINISH.region) },
                Actions.fadeIn(0.3f),
            ))
        }
    }

    private suspend fun minusSizeMB(block: () -> Unit) = CompletableDeferred<Unit>().also { continuation ->
        var step: Int
        while (coroutineContext.isActive && sizeMBFlow.value > 0) {
            step = (1..7).shuffled().first()
            sizeMBFlow.apply { value = if ((value - step) < 0) 0 else value - step }
            delay((10..15).shuffled().first().toLong())
        }
        continuation.complete(Unit)
        block()
    }.await()

    fun cleaning() {
        coroutine.launch {
            runGDX { imageStartAnim() }
            
            val orbListAnimJob = launch { orbListAnim() }
            minusSizeMB {
                orbListAnimJob.cancel()
                runGDX { imageFinishAnim() }
            }
        }
    }



    object Left: Side {
        override val x: Float = -100f
        override val y: Float get() = (-300..872).shuffled().first().toFloat()
        override val position get() = Vector2(x, y)
    }

    object Right: Side {
        override val x: Float = 700f
        override val y: Float get() = (-300..872).shuffled().first().toFloat()
        override val position get() = Vector2(x, y)
    }

    object Top: Side {
        override val x: Float get() = (0..700).shuffled().first().toFloat()
        override val y: Float = 872f
        override val position get() = Vector2(x, y)
    }

    interface Side {
        val x: Float
        val y: Float
        val position: Vector2
    }

}