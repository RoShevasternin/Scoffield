package com.filermax.detoxer.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.game.actors.label.ALabelStyle
import com.filermax.detoxer.game.utils.GameColor
import com.filermax.detoxer.game.utils.actor.disable
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import com.filermax.detoxer.game.utils.runGDX
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.filermax.detoxer.game.actors.label.ALabelStyle.Mulish.Bold as SMBold
import com.filermax.detoxer.game.actors.label.ALabelStyle.Mulish.Medium as SMMedium
import com.filermax.detoxer.game.utils.Layout.Title as LT

class Title(
    private val titleText: String,
): AdvancedGroup() {

    private val titleLabel       = Label(titleText, ALabelStyle.style(SMBold._45, Color.BLACK))
    private val descriptionLabel = Label("", ALabelStyle.style(SMMedium._33, GameColor.black_60))



    override fun sizeChanged() {
        super.sizeChanged()
        disable()
        if(width > 0 && height > 0) addActorsOnGroup()
    }


    private fun addActorsOnGroup() {
        addLabels()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addLabels() {
        addActors(titleLabel, descriptionLabel)
        titleLabel.apply {
            setBounds(LT.title)
            setAlignment(Align.center)
        }
        descriptionLabel.apply {
            setBounds(LT.description)
            setAlignment(Align.center)
            addAction(Actions.alpha(0f))
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    suspend fun showSuccessfully(description: String) = suspendCoroutine<Unit> { continuation ->
        runGDX {
            titleLabel.apply {
                addAction(Actions.sequence(
                    Actions.fadeOut(0.3f),
                    Actions.run { setText("Successfully") },
                    Actions.fadeIn(0.3f),
                    Actions.moveTo(LT.title.x, LT.titleEndY, 0.5f),
                    Actions.run {
                        descriptionLabel.apply {
                            setText(description)
                            addAction(Actions.sequence(
                                Actions.fadeIn(0.5f),
                                Actions.run { continuation.resume(Unit) }
                            ))
                        }
                    }
                ))
            }
        }
    }

    suspend fun hideSuccessfully() = suspendCoroutine<Unit> { continuation ->
        runGDX {
            descriptionLabel.apply {
                addAction(Actions.sequence(
                    Actions.fadeOut(0.5f),
                    Actions.run {
                        titleLabel.apply {
                            addAction(Actions.sequence(
                                Actions.fadeOut(0.3f),
                                Actions.run { setText(titleText) },
                                Actions.fadeIn(0.3f),
                                Actions.moveTo(LT.title.x, LT.title.y, 0.5f),
                                Actions.run { continuation.resume(Unit) }
                            ))
                        }
                    }
                ))
            }
        }
    }

}