package com.watermil.terwater.game.screens

import com.watermil.terwater.game.actors.main.AMainResultTry
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.advanced.AdvancedMainScreen
import com.watermil.terwater.game.utils.advanced.AdvancedStage
import com.watermil.terwater.game.utils.gdxGame
import com.watermil.terwater.game.utils.region

class ResultTryScreen: AdvancedMainScreen() {

    override val aMain = AMainResultTry(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.mda.region)
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