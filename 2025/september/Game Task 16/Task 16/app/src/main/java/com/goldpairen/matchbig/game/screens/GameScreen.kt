package com.goldpairen.matchbig.game.screens

import com.goldpairen.matchbig.game.actors.main.AMainGame
import com.goldpairen.matchbig.game.utils.Block
import com.goldpairen.matchbig.game.utils.advanced.AdvancedMainScreen
import com.goldpairen.matchbig.game.utils.advanced.AdvancedStage
import com.goldpairen.matchbig.game.utils.gdxGame
import com.goldpairen.matchbig.game.utils.region

class GameScreen: AdvancedMainScreen() {

    override val aMain = AMainGame(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.ORIGIN.region)
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