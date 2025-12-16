package com.sandtro.trintity.game.screens

import com.sandtro.trintity.game.actors.main.AMainResultWin
import com.sandtro.trintity.game.utils.Block
import com.sandtro.trintity.game.utils.advanced.AdvancedMainScreen
import com.sandtro.trintity.game.utils.advanced.AdvancedStage
import com.sandtro.trintity.game.utils.gdxGame
import com.sandtro.trintity.game.utils.region

class ResultWinScreen: AdvancedMainScreen() {

    override val aMain = AMainResultWin(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.WINN.region)
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