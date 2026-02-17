package com.appwin.fight.game.screens

import com.appwin.fight.game.actors.main.AMainPuzlaks
import com.appwin.fight.game.utils.Block
import com.appwin.fight.game.utils.advanced.AdvancedMainScreen
import com.appwin.fight.game.utils.advanced.AdvancedStage
import com.appwin.fight.game.utils.gdxGame
import com.appwin.fight.game.utils.region

class PazzleScreen: AdvancedMainScreen() {

    override val aMain = AMainPuzlaks(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACK3.region)
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