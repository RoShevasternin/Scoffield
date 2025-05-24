package com.plinki.kollinllabs.game.screens

import com.plinki.kollinllabs.game.actors.main.AMainRules
import com.plinki.kollinllabs.game.utils.Block
import com.plinki.kollinllabs.game.utils.advanced.AdvancedMainScreen
import com.plinki.kollinllabs.game.utils.advanced.AdvancedStage
import com.plinki.kollinllabs.game.utils.gdxGame
import com.plinki.kollinllabs.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

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