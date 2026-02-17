package com.icertif.pyrzzle.game.actors.puzzle

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.icertif.pyrzzle.game.utils.actor.disable
import com.icertif.pyrzzle.game.utils.actor.enable
import com.icertif.pyrzzle.game.utils.advanced.AdvancedGroup
import com.icertif.pyrzzle.game.utils.advanced.AdvancedScreen

class APuzzle(
    override val screen: AdvancedScreen,
    private val textureRegion: TextureRegion,
): AdvancedGroup() {

    var doAfterRotate: (Float) -> Unit = { }

    override fun addActorsOnGroup() {
        addListener(getInputAdapter())

    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

    private fun getInputAdapter() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            screen.game.soundUtil.apply { play(glass) }
            disable()
            addAction(Actions.sequence(
                Actions.rotateBy(90f, 0.3f, Interpolation.fade),
                Actions.run {
                    doAfterRotate(rotation)
                    enable()
                }
            ))
            return true
        }
    }

}