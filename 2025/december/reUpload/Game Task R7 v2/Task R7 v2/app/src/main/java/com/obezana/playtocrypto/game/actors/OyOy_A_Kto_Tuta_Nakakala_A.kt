package com.obezana.playtocrypto.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.obezana.playtocrypto.game.manager.SpriteManager
import com.obezana.playtocrypto.game.utils.actor.disable
import com.obezana.playtocrypto.game.utils.advanced.AdvancedGroup
import com.obezana.playtocrypto.game.utils.advanced.AdvancedStage

class OyOy_A_Kto_Tuta_Nakakala_A: AdvancedGroup() {

    private val zamokVeImg    = Image(SpriteManager.GameRegion.gorochki_and_zamok.region)
    private val uebokImg      = Image(SpriteManager.GameRegion.Uebok.region)
    private val garshokImg    = Image(SpriteManager.GameRegion.Gorshok.region)
    private val skalaImg      = Image(SpriteManager.GameRegion.skala.region)
    private val t1Img      = Image(SpriteManager.GameRegion.taravavava.region)
    private val t2Img      = Image(SpriteManager.GameRegion.taravavava.region)


    init {
        disable()
        addUebok()
    }

    private fun addUebok() {
        addActors(zamokVeImg, uebokImg, garshokImg, t1Img, t2Img, skalaImg)
        zamokVeImg.apply {
            setBounds(26f, -176f, 1696f, 549f)
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.moveTo(-125f, 0f, 2f),
                        Actions.moveTo(26f, -176f, 5f),
                    ))
            )
        }
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
        skalaImg.apply {
            setBounds(-21f, -30f, 601f, 177f)
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.moveTo( -3f, 0f,1f, Interpolation.sineOut),
                        Actions.moveTo( -21f, -30f, 2f, Interpolation.pow5),
                    ))
            )
        }
        t1Img.apply {
            setBounds(191f, 0f, 307f, 67f)
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.moveBy( 20f, 0f,0.7f),
                        Actions.moveBy( -20f,0f, 0.7f),
                    )))
        }
        t2Img.apply {
            setBounds(359f, 0f, 258f, 57f)
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.moveBy( -20f, 0f,0.7f),
                        Actions.moveBy( 20f,0f, 0.7f),
                    )))
        }

    }

}