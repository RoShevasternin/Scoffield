package com.sandtro.trintity.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sandtro.trintity.game.utils.actor.disable
import com.sandtro.trintity.game.utils.advanced.AdvancedGroup
import com.sandtro.trintity.game.utils.advanced.AdvancedScreen

class AToy(override val screen: AdvancedScreen, region: TextureRegion): AdvancedGroup() {

    private val img = Image(region)

    override fun addActorsOnGroup() {
        addAndFillActor(img)
        img.disable()
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun selected() {
        clearActions()
        addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.scaleBy(-0.25f, -0.25f, 0.25f, Interpolation.circle),
                    Actions.scaleBy(0.25f, 0.25f, 0.25f, Interpolation.circle),
                )
            )
        )
    }

    fun unselected() {
        clearActions()
        addAction(Actions.scaleTo(1f, 1f, 0.25f))
    }

}