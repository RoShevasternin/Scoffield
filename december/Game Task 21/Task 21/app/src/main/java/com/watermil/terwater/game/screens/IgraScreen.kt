package com.watermil.terwater.game.screens

import com.watermil.terwater.game.actors.main.AMainIgra
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.advanced.AdvancedMainScreen
import com.watermil.terwater.game.utils.advanced.AdvancedStage
import com.watermil.terwater.game.utils.gdxGame
import com.watermil.terwater.game.utils.region

class IgraScreen: AdvancedMainScreen() {

    override val aMain = AMainIgra(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.water)
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