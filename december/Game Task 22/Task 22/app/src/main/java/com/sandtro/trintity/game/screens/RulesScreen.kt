package com.sandtro.trintity.game.screens

import com.sandtro.trintity.game.actors.main.AMainRules
import com.sandtro.trintity.game.utils.Block
import com.sandtro.trintity.game.utils.advanced.AdvancedMainScreen
import com.sandtro.trintity.game.utils.advanced.AdvancedStage
import com.sandtro.trintity.game.utils.gdxGame
import com.sandtro.trintity.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.DRAK.region)
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