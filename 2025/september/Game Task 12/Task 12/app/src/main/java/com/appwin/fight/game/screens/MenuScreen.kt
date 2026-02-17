package com.appwin.fight.game.screens

import com.appwin.fight.game.actors.main.AMainMenu
import com.appwin.fight.game.utils.Block
import com.appwin.fight.game.utils.advanced.AdvancedMainScreen
import com.appwin.fight.game.utils.advanced.AdvancedStage
import com.appwin.fight.game.utils.gdxGame
import com.appwin.fight.game.utils.region

class MenuScreen: AdvancedMainScreen() {

    override val aMain = AMainMenu(this)

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