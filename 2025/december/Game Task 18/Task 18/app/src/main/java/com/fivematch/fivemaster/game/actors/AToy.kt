package com.fivematch.fivemaster.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fivematch.fivemaster.game.utils.actor.disable
import com.fivematch.fivemaster.game.utils.advanced.AdvancedGroup
import com.fivematch.fivemaster.game.utils.advanced.AdvancedScreen

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
        addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-0.2f, -0.2f, 0.2f, Interpolation.circle),
            Actions.scaleBy(0.2f, 0.2f, 0.2f, Interpolation.circle),
        )))
    }

    fun unselected() {
        clearActions()
        addAction(Actions.scaleTo(1f, 1f, 0.2f))
    }

}