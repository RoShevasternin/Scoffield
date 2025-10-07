package com.goldpairen.matchbig.game.screens

import com.goldpairen.matchbig.game.actors.main.AMainRules
import com.goldpairen.matchbig.game.utils.Block
import com.goldpairen.matchbig.game.utils.advanced.AdvancedMainScreen
import com.goldpairen.matchbig.game.utils.advanced.AdvancedStage
import com.goldpairen.matchbig.game.utils.gdxGame
import com.goldpairen.matchbig.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BLURED.region)
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