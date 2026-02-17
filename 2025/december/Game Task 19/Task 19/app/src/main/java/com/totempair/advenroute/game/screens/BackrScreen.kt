package com.totempair.advenroute.game.screens

import com.totempair.advenroute.game.actors.main.AMainBackr
import com.totempair.advenroute.game.actors.main.AMainMenu
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.advanced.AdvancedMainScreen
import com.totempair.advenroute.game.utils.advanced.AdvancedStage
import com.totempair.advenroute.game.utils.gdxGame
import com.totempair.advenroute.game.utils.region

class BackrScreen: AdvancedMainScreen() {

    override val aMain = AMainBackr(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACK2.region)
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