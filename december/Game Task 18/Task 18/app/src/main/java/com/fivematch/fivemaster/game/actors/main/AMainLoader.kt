package com.fivematch.fivemaster.game.actors.main

import com.fivematch.fivemaster.game.screens.LoaderScreen
import com.fivematch.fivemaster.game.utils.Acts
import com.fivematch.fivemaster.game.utils.advanced.AdvancedGroup
import com.fivematch.fivemaster.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLoader = Image(gdxGame.assetsLoader.loader)

//    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActor(imgLoader)
        imgLoader.setBounds(137f, 556f, 807f, 807f)
        imgLoader.setOrigin(Align.center)

        imgLoader.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(1.12f, 1.12f, 0.35f),
            Acts.scaleTo(1f, 1f, 0.35f),
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