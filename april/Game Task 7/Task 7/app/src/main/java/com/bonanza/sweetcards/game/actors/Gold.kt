package com.bonanza.sweetcards.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.bonanza.sweetcards.game.actors.image.AImage
import com.bonanza.sweetcards.game.manager.SpriteManager
import com.bonanza.sweetcards.game.util.advanced.AdvancedGroup

class Gold: AdvancedGroup(), OC {

    private val gold = AImage(SpriteManager.GameRegion.GOLD.region)
    val image        = AImage()



    override fun sizeChanged() {
        super.sizeChanged()

        if (width > 0 && height > 0) {
            addActor(image)
            addAndFillActor(gold)

            image.apply {
                addAction(Actions.alpha(0f))
                setBounds(8f, 23f, 110f, 110f)
            }
        }
    }



    override fun open(block: () -> Unit) {
        gold.addAction(Actions.fadeOut(0.4f))
        image.addAction(Actions.sequence(
            Actions.fadeIn(0.4f),
            Actions.run { block() }
        ))
    }

    override fun close(block: () -> Unit) {
        gold.addAction(Actions.fadeIn(0.4f))
        image.addAction(Actions.sequence(
            Actions.fadeOut(0.4f),
            Actions.run { block() }
        ))
    }

}