package com.appwin.fight.game.screens

import com.appwin.fight.game.actors.main.AMainSettings
import com.appwin.fight.game.utils.Block
import com.appwin.fight.game.utils.advanced.AdvancedMainScreen
import com.appwin.fight.game.utils.advanced.AdvancedStage
import com.appwin.fight.game.utils.gdxGame
import com.appwin.fight.game.utils.region

class SettingsScreen: AdvancedMainScreen() {

    override val aMain = AMainSettings(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACK2.region)
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