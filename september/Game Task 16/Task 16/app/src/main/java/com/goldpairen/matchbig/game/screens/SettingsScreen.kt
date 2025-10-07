package com.goldpairen.matchbig.game.screens

import com.goldpairen.matchbig.game.actors.main.AMainSettings
import com.goldpairen.matchbig.game.utils.Block
import com.goldpairen.matchbig.game.utils.advanced.AdvancedMainScreen
import com.goldpairen.matchbig.game.utils.advanced.AdvancedStage
import com.goldpairen.matchbig.game.utils.gdxGame
import com.goldpairen.matchbig.game.utils.region

class SettingsScreen: AdvancedMainScreen() {

    override val aMain = AMainSettings(this)

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