package com.puzfortunes.twinst.game.actors.main

import com.puzfortunes.twinst.game.screens.LoaderScreen
import com.puzfortunes.twinst.game.utils.Acts
import com.puzfortunes.twinst.game.utils.advanced.AdvancedGroup
import com.puzfortunes.twinst.game.utils.gdxGame
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
        imgLoader.setBounds(431f, 851f, 219f, 219f)
        imgLoader.setOrigin(Align.center)

        imgLoader.addAction(Acts.forever(Acts.rotateBy(360f, 1.25f)))
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