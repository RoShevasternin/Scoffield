package com.order.offortute.game.screens

import com.order.offortute.game.actors.main.AMainRules
import com.order.offortute.game.utils.Block
import com.order.offortute.game.utils.advanced.AdvancedMainScreen
import com.order.offortute.game.utils.advanced.AdvancedStage
import com.order.offortute.game.utils.gdxGame
import com.order.offortute.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKG.region)
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