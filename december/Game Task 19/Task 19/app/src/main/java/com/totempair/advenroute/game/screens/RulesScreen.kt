package com.totempair.advenroute.game.screens

import com.totempair.advenroute.game.actors.main.AMainRules
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.advanced.AdvancedMainScreen
import com.totempair.advenroute.game.utils.advanced.AdvancedStage
import com.totempair.advenroute.game.utils.gdxGame
import com.totempair.advenroute.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACK1.region)
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