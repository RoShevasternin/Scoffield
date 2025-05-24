package com.bonanza.pazzleground.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.bonanza.pazzleground.game.screens.LoaderScreen
import com.bonanza.pazzleground.game.utils.Acts
import com.bonanza.pazzleground.game.utils.advanced.AdvancedGroup
import com.bonanza.pazzleground.game.utils.gdxGame

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
        imgLoader.setBounds(344f, 283f, 392f, 392f)
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