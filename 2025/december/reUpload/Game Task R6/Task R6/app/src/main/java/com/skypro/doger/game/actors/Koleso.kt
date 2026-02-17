package com.skypro.doger.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.skypro.doger.game.manager.SpriteManager
import com.skypro.doger.game.utils.advanced.AdvancedGroup

class Koleso: AdvancedGroup() {

    private val image = Image(SpriteManager.SplashRegion.KOLESO.region)



    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }


    private fun addActorsOnGroup() {
        addActor(image)
        image.apply {
            setBounds(-500f, -500f, 1000f, 1000f)
            setOrigin(Align.center)
            setScale(4.608f)
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    fun startAnim() {
        addAction(Actions.forever(Actions.rotateBy(-360f, 20f)))
    }
}
