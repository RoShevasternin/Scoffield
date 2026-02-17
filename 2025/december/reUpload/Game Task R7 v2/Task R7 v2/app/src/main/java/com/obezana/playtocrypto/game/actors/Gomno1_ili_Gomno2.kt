package com.obezana.playtocrypto.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.obezana.playtocrypto.game.manager.SpriteManager
import com.obezana.playtocrypto.game.utils.actor.disable
import com.obezana.playtocrypto.game.utils.advanced.AdvancedGroup
import com.obezana.playtocrypto.game.utils.advanced.AdvancedStage

class Gomno1_ili_Gomno2: AdvancedGroup() {

    private val uebokImg      = Image(SpriteManager.GameRegion.Uebok.region)
    private val garshokImg    = Image(SpriteManager.GameRegion.Gorshok.region)
    private val travaUDomaImg = Image(SpriteManager.GameRegion.travka.region)


    init {
        disable()
        addUebok()
    }

    private fun addUebok() {
        addActors(uebokImg, garshokImg, travaUDomaImg)
        uebokImg.apply {
            setBounds(-72f, 159f, 426f, 236f)
            addAction(
                Actions.forever(
                    Actions.sequence(
                Actions.moveBy(0f, 10f, 1f),
                Actions.moveBy(0f, -20f, 1f),
                Actions.moveBy(0f, 10f, 1f),
            )))
        }
        garshokImg.apply {
            setBounds(-142f, 0f, 652f, 177f)
            addAction(
                Actions.forever(
                    Actions.sequence(
                Actions.moveBy( 10f, 0f,1f),
                Actions.moveBy( -20f,0f, 1f),
                Actions.moveBy( 10f, 0f,1f),
            )))
        }
        travaUDomaImg.apply {
            setBounds(-14f, -31f, 1569f, 220f)
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.moveTo( -14f, 0f,0.8f),
                        Actions.moveTo( -14f,-31f, 1f),
                    ))
            )
        }
    }

}