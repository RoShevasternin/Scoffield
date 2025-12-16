package com.tikispit.ilets.game.actors.main

import com.tikispit.ilets.game.screens.LoaderScreen
import com.tikispit.ilets.game.utils.Acts
import com.tikispit.ilets.game.utils.advanced.AdvancedGroup
import com.tikispit.ilets.game.utils.gdxGame
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
        imgLoader.setBounds(422f, 842f, 236f, 236f)
        imgLoader.setOrigin(Align.center)

        // Rotate
        imgLoader.addAction(Acts.forever(Acts.rotateBy(-360f, 1f)))

        // Scale
        //imgLoader.addAction(Acts.forever(Acts.sequence(
        //    Acts.scaleTo(1.134f, 1.134f, 0.36f),
        //    Acts.scaleTo(1f, 1f, 0.36f),
        //)))
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