package com.treasurebig.twins.game.screens

import com.treasurebig.twins.game.actors.main.AMainRules
import com.treasurebig.twins.game.utils.Block
import com.treasurebig.twins.game.utils.advanced.AdvancedMainScreen
import com.treasurebig.twins.game.utils.advanced.AdvancedStage
import com.treasurebig.twins.game.utils.gdxGame
import com.treasurebig.twins.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACK1.region)
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