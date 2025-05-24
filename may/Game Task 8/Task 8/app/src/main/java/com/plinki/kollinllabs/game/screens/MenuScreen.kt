package com.plinki.kollinllabs.game.screens

import com.plinki.kollinllabs.game.actors.main.AMainMenu
import com.plinki.kollinllabs.game.utils.Block
import com.plinki.kollinllabs.game.utils.WIDTH_UI
import com.plinki.kollinllabs.game.utils.actor.setSizeScaled
import com.plinki.kollinllabs.game.utils.advanced.AdvancedMainScreen
import com.plinki.kollinllabs.game.utils.advanced.AdvancedStage
import com.plinki.kollinllabs.game.utils.gdxGame
import com.plinki.kollinllabs.game.utils.region

class MenuScreen: AdvancedMainScreen() {

    override val aMain = AMainMenu(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }
}