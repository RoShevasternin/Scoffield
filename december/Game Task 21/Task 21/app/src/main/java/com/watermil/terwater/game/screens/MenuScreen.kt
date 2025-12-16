package com.watermil.terwater.game.screens

import com.watermil.terwater.game.actors.main.AMainMenu
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.advanced.AdvancedMainScreen
import com.watermil.terwater.game.utils.advanced.AdvancedStage
import com.watermil.terwater.game.utils.gdxGame
import com.watermil.terwater.game.utils.region

class MenuScreen: AdvancedMainScreen() {

    override val aMain = AMainMenu(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.water.region)
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