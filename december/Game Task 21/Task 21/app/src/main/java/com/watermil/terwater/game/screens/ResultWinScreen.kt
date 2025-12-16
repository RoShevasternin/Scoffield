package com.watermil.terwater.game.screens

import com.watermil.terwater.game.actors.main.AMainResultWin
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.advanced.AdvancedMainScreen
import com.watermil.terwater.game.utils.advanced.AdvancedStage
import com.watermil.terwater.game.utils.gdxGame
import com.watermil.terwater.game.utils.region

class ResultWinScreen: AdvancedMainScreen() {

    override val aMain = AMainResultWin(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.yha.region)
        //addAndFillActor(Image(gdxGame.assetsAll.WIN))
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