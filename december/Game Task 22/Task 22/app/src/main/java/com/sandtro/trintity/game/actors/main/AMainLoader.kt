package com.sandtro.trintity.game.actors.main

import com.sandtro.trintity.game.screens.LoaderScreen
import com.sandtro.trintity.game.utils.Acts
import com.sandtro.trintity.game.utils.advanced.AdvancedGroup
import com.sandtro.trintity.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLoader = Image(gdxGame.assetsLoader.loader)

    // private val progress = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActor(imgLoader)
        imgLoader.setBounds(448f, 868f, 184f, 184f)
        imgLoader.setOrigin(Align.center)

        imgLoader.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(1.105f, 1.105f, 0.23f),
            Acts.scaleTo(1f, 1f, 0.23f),
        )))
        imgLoader.addAction(Acts.forever(Acts.rotateBy(-360f, 0.7f)))
    }

    private fun addProgress() {
        //addActor(progress)
        //progress.setBounds(67f, 687f, 558f, 8f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        //progress.progressPercentFlow.value = percent.toFloat()
    }

}