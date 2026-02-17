package com.appwin.fight.game.actors.main

import com.appwin.fight.game.screens.LoaderScreen
import com.appwin.fight.game.utils.Acts
import com.appwin.fight.game.utils.advanced.AdvancedGroup
import com.appwin.fight.game.utils.gdxGame
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
        imgLoader.setBounds(392f, 811f, 297f, 297f)
        imgLoader.setOrigin(Align.center)

        imgLoader.addAction(Acts.forever(Acts.rotateBy(360f, 1f)))
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