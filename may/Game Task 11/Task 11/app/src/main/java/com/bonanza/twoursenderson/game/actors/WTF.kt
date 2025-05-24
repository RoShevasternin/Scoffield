package com.bonanza.twoursenderson.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedGroup
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedScreen
import com.bonanza.twoursenderson.game.utils.gdxGame

class WTF(override val screen: AdvancedScreen): AdvancedGroup(), OpenClose {

    private val grey = Image(gdxGame.assetsAll.wtf)
    val image        = Image()

    override fun addActorsOnGroup() {
        addActor(image)
        addAndFillActor(grey)

        image.apply {
            addAction(Actions.alpha(0f))
            setBounds(34f, 30f, 200f, 200f)
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

interface OpenClose {
    fun open(block: () -> Unit = {})
    fun close(block: () -> Unit = {})
}