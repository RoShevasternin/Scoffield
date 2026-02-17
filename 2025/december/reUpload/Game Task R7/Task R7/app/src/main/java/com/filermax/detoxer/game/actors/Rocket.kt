package com.filermax.detoxer.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.game.manager.SpriteManager
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import com.filermax.detoxer.game.utils.Layout.Rocket as LR

class Rocket: AdvancedGroup() {

    private val image = Image(SpriteManager.BoostRegion.ROCKET.region)
    private val star1 = Image(SpriteManager.BoostRegion.STAR_MINI.region)
    private val star2 = Image(SpriteManager.BoostRegion.STAR_MINI.region)
    private val star3 = Image(SpriteManager.BoostRegion.STAR_BIG.region)


    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }


    private fun addActorsOnGroup() {
        addRocket()
        addStars()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addRocket() {
        addActor(image)
        image.apply {
            setBounds(0f, 0f, this@Rocket.width, this@Rocket.height)
        }
    }

    private fun addStars() {
        addActors(star1, star2, star3)
        star1.apply {
            setBounds(LR.star1)
            setOrigin(Align.center)
        }
        star2.apply {
            setBounds(LR.star2)
            setOrigin(Align.center)
        }
        star3.apply {
            setBounds(LR.star3)
            setOrigin(Align.center)
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    fun startAnim() {
        setOrigin(Align.topRight)
        val scaleValue = 0.1f
        addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-scaleValue, -scaleValue, 0.3f, Interpolation.fade),
            Actions.scaleBy(scaleValue, scaleValue, 0.3f, Interpolation.fade),
        )))

        star1.addAction(Actions.forever(Actions.rotateBy(360f, 2f)))
        star2.addAction(Actions.forever(Actions.rotateBy(-360f, 3f)))
        star3.addAction(Actions.forever(Actions.rotateBy(360f, 5f)))
    }

    fun finishAnim() {
        clearActions()
        addAction(Actions.scaleTo(0f, 0f, 0.7f, Interpolation.swing))
    }

}