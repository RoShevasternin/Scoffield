package com.plinkray.ioosudmarine.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.plinkray.ioosudmarine.game.screens.LoaderScreen
import com.plinkray.ioosudmarine.game.utils.Acts
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedGroup
import com.plinkray.ioosudmarine.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgL1 = Image(gdxGame.assetsLoader.loader)

//    private val progress  = AProgressLoader(screen)

    override fun addActorsOnGroup() {
        addImgLL()

        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLL() {
        addActors(imgL1)
        imgL1.setBounds(361f, 285f, 358f, 405f)
        imgL1.setOrigin(Align.center)
        imgL1.addAction(Acts.forever(Acts.rotateBy(360f, 1.25f, Interpolation.fastSlow)))

        //imgTitle.setOrigin(Align.center)
        //imgTitle.addAction(Acts.forever(Acts.parallel(Acts.rotateBy(-360f, 12f, Interpolation.linear))))
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