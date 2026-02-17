package com.fivematch.fivemaster.game.screens

import com.fivematch.fivemaster.game.actors.main.AMainIgra
import com.fivematch.fivemaster.game.actors.main.AMainRules
import com.fivematch.fivemaster.game.utils.Block
import com.fivematch.fivemaster.game.utils.advanced.AdvancedMainScreen
import com.fivematch.fivemaster.game.utils.advanced.AdvancedStage
import com.fivematch.fivemaster.game.utils.gdxGame
import com.fivematch.fivemaster.game.utils.region

class IgraScreen: AdvancedMainScreen() {

    override val aMain = AMainIgra(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI ------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }
}