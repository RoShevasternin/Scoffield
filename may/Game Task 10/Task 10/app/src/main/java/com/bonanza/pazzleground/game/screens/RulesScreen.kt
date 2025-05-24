package com.bonanza.pazzleground.game.screens

import com.bonanza.pazzleground.game.actors.main.AMainRulkes
import com.bonanza.pazzleground.game.utils.Block
import com.bonanza.pazzleground.game.utils.advanced.AdvancedMainScreen
import com.bonanza.pazzleground.game.utils.advanced.AdvancedStage
import com.bonanza.pazzleground.game.utils.gdxGame
import com.bonanza.pazzleground.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRulkes(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
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