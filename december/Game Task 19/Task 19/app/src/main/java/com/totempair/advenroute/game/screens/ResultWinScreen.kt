package com.totempair.advenroute.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.totempair.advenroute.game.actors.main.AMainResultWin
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.advanced.AdvancedMainScreen
import com.totempair.advenroute.game.utils.advanced.AdvancedStage
import com.totempair.advenroute.game.utils.gdxGame
import com.totempair.advenroute.game.utils.region

class ResultWinScreen: AdvancedMainScreen() {

    override val aMain = AMainResultWin(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACK1.region)
        addAndFillActor(Image(gdxGame.assetsAll.WIN))
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