package com.bonanza.sweetcards.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.bonanza.sweetcards.game.actors.image.AImage
import com.bonanza.sweetcards.game.manager.SpriteManager
import com.bonanza.sweetcards.game.util.advanced.AdvancedGroup

class Grey: AdvancedGroup(), OC {

    private val grey = AImage(SpriteManager.GameRegion.GRAY.region)
    val image        = AImage()



    override fun sizeChanged() {
        super.sizeChanged()

        if (width > 0 && height > 0) {
            addActor(image)
            addAndFillActor(grey)

            image.apply {
                addAction(Actions.alpha(0f))
                setBounds(7f, 23f, 110f, 110f)
            }
        }
    }



    override fun open(block: () -> Unit) {
        grey.addAction(Actions.fadeOut(0.1f))
        image.addAction(Actions.sequence(
            Actions.fadeIn(0.1f),
            Actions.run { block() }
        ))
    }

    override fun close(block: () -> Unit) {
        grey.addAction(Actions.fadeIn(0.1f))
        image.addAction(Actions.sequence(
            Actions.fadeOut(0.1f),
            Actions.run { block() }
        ))
    }

}

interface OC {
    fun open(block: () -> Unit = {})
    fun close(block: () -> Unit = {})
}