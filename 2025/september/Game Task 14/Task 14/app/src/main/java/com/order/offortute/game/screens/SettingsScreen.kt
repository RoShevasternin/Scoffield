package com.order.offortute.game.screens

import com.order.offortute.game.actors.main.AMainSettings
import com.order.offortute.game.utils.Block
import com.order.offortute.game.utils.advanced.AdvancedMainScreen
import com.order.offortute.game.utils.advanced.AdvancedStage
import com.order.offortute.game.utils.gdxGame
import com.order.offortute.game.utils.region

class SettingsScreen: AdvancedMainScreen() {

    override val aMain = AMainSettings(this)

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