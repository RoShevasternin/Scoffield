package com.tikispit.ilets.game.screens

import com.tikispit.ilets.game.actors.main.AMainRules
import com.tikispit.ilets.game.utils.Block
import com.tikispit.ilets.game.utils.advanced.AdvancedMainScreen
import com.tikispit.ilets.game.utils.advanced.AdvancedStage
import com.tikispit.ilets.game.utils.gdxGame
import com.tikispit.ilets.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKGREG.region)
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