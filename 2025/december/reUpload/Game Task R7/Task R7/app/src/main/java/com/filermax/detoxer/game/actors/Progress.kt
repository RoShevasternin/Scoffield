package com.filermax.detoxer.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.game.actors.label.ALabelStyle
import com.filermax.detoxer.game.manager.SpriteManager
import com.filermax.detoxer.game.utils.GameColor
import com.filermax.detoxer.game.utils.actor.disable
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import com.filermax.detoxer.game.utils.hideTime
import com.filermax.detoxer.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.filermax.detoxer.game.actors.label.ALabelStyle.Mulish.Bold as SMBold
import com.filermax.detoxer.game.actors.label.ALabelStyle.Mulish.Medium as SMMedium
import com.filermax.detoxer.game.utils.Layout.Progress as LP

class Progress(
    private val title : String,
    private val param1: String,
    private val param2: String,
    private val param3: String,
    private val param4: String,
): AdvancedGroup() {

    val percentFlow = MutableStateFlow(0)

    private val titleLabel  = Label(title, ALabelStyle.style(SMBold._45, GameColor.blue))

    private val group       = AdvancedGroup()
    private val param1Label = Label(param1, ALabelStyle.style(SMMedium._30, GameColor.black_60))
    private val param2Label = Label(param2, ALabelStyle.style(SMMedium._30, GameColor.black_60))
    private val param3Label = Label(param3, ALabelStyle.style(SMMedium._30, GameColor.black_60))
    private val param4Label = Label(param4, ALabelStyle.style(SMMedium._30, GameColor.black_60))

    private val paramLabelList  = listOf(param1Label, param2Label, param3Label, param4Label)
    private val statusImageList = List(4) { Image(SpriteManager.CommonRegion.LOADER.region) }



    override fun sizeChanged() {
        super.sizeChanged()
        disable()
        if(width > 0 && height > 0) addActorsOnGroup()
    }


    private fun addActorsOnGroup() {
        addLabels()
        addLoaders()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addLabels() {
        addActors(titleLabel, group)
        titleLabel.apply {
            setBounds(LP.title)
            setAlignment(Align.center)
        }

        group.apply {
            setBounds(LP.group)
            var ny = LP.param.y
            paramLabelList.onEach { param ->
                addActor(param)
                param.apply {
                    with(LP.param) {
                        param.setBounds(x, ny, w, h)
                        ny -= vs + h
                    }
                }
            }
        }

        collectPercent()
    }

    private fun addLoaders() {
        group.apply {
            var ny = LP.status.y
            statusImageList.onEach { status ->
                addActor(status)
                status.apply {
                    with(LP.status) {
                        setBounds(x, ny, w, h)
                        ny -= vs + h
                    }
                    setOrigin(Align.center)
                    addAction(Actions.forever(Actions.rotateBy(-360f, 5f)))
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun Image.statusDone() {
        clearActions()
        addAction(Actions.rotateTo(0f))
        drawable = TextureRegionDrawable(SpriteManager.CommonRegion.DONE.region)
    }

    private fun collectPercent() {
        coroutine.launch {
            percentFlow.collect { percent ->
                runGDX {
                    when (percent) {
                        in 50..69 -> statusImageList[0]
                        in 70..79 -> statusImageList[1]
                        in 80..99 -> statusImageList[2]
                        100       -> statusImageList[3]
                        else      -> Image()
                    }.also { it.statusDone() }

                    titleLabel.setText("$title $percent%")
                }
            }
        }
    }

    suspend fun showDone() = suspendCoroutine<Unit> { continuation ->
        titleLabel.addAction(Actions.sequence(
            Actions.fadeOut(hideTime),
            Actions.run {
                group.addAction(Actions.sequence(
                    Actions.moveTo(LP.group.x, LP.groupY, 0.7f),
                    Actions.run {
                        paramLabelList.onEach { it.style.fontColor = Color.BLACK }
                        continuation.resume(Unit)
                    },
                ))
            }
        ))
    }

}