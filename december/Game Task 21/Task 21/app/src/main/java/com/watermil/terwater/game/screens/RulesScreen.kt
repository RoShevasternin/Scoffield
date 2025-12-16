package com.watermil.terwater.game.screens

import com.watermil.terwater.game.actors.main.AMainRules
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.advanced.AdvancedMainScreen
import com.watermil.terwater.game.utils.advanced.AdvancedStage
import com.watermil.terwater.game.utils.gdxGame
import com.watermil.terwater.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.ridingtone.region)
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