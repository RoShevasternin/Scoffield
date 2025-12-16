package com.hexofun.rundeo.game.actors

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.hexofun.rundeo.game.utils.actor.setBounds
import com.hexofun.rundeo.game.utils.advanced.AdvancedGroup
import com.hexofun.rundeo.game.utils.advanced.AdvancedScreen

class AHexagon(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.allAssets.hexagon))
        addColors()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addColors() {
        val size = Vector2(10f, 10f)

        listOf(
            Vector2(81f, 156f),
            Vector2(147f, 119f),
            Vector2(147f, 44f),
            Vector2(81f, 5f),
            Vector2(19f, 40f),
            Vector2(19f, 119f),
        ).onEach { pos ->
            addActor(Image().apply {
                setBounds(pos, size)
                addAction(Actions.forever(Actions.sequence(
                    Actions.delay(0.25f),
                    Actions.run { drawable = TextureRegionDrawable(screen.game.allAssets.colors.random()) }
                )))
            })
        }
    }

}