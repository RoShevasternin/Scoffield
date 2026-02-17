package com.bonanza.twoursenderson.game.screens

import com.bonanza.twoursenderson.game.actors.main.AMainGame
import com.bonanza.twoursenderson.game.utils.Block
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedMainScreen
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedStage
import com.bonanza.twoursenderson.game.utils.gdxGame
import com.bonanza.twoursenderson.game.utils.region

class GameScreen: AdvancedMainScreen() {

    override val aMain = AMainGame(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKGR.region)
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