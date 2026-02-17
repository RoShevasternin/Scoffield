package com.watermil.terwater.game.actors.main

import com.watermil.terwater.game.screens.LoaderScreen
import com.watermil.terwater.game.utils.Acts
import com.watermil.terwater.game.utils.advanced.AdvancedGroup
import com.watermil.terwater.game.utils.gdxGame
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