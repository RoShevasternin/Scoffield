package com.goldpairen.matchbig.game.actors.main

import com.goldpairen.matchbig.game.screens.LoaderScreen
import com.goldpairen.matchbig.game.utils.Acts
import com.goldpairen.matchbig.game.utils.advanced.AdvancedGroup
import com.goldpairen.matchbig.game.utils.gdxGame
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
        imgLoader.setBounds(386f, 458f, 307f, 307f)
        imgLoader.setOrigin(Align.center)

        imgLoader.addAction(Acts.forever(Acts.rotateBy(360f, 0.85f)))
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