package com.totempair.advenroute.game.actors.main

import com.totempair.advenroute.game.screens.LoaderScreen
import com.totempair.advenroute.game.utils.Acts
import com.totempair.advenroute.game.utils.advanced.AdvancedGroup
import com.totempair.advenroute.game.utils.gdxGame
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
        imgLoader.setBounds(451f, 877f, 177f, 165f)
        imgLoader.setOrigin(Align.center)

        imgLoader.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(1.134f, 1.134f, 0.36f),
            Acts.scaleTo(1f, 1f, 0.36f),
        )))
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