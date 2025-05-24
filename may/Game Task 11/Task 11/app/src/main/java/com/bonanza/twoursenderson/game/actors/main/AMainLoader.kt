package com.bonanza.twoursenderson.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.bonanza.twoursenderson.game.screens.LoaderScreen
import com.bonanza.twoursenderson.game.utils.Acts
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedGroup
import com.bonanza.twoursenderson.game.utils.gdxGame

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
        imgLoader.setBounds(857f, 457f, 286f, 286f)
        imgLoader.setOrigin(Align.center)

        imgLoader.addAction(Acts.forever(Acts.rotateBy(-360f, 1.2f)))
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