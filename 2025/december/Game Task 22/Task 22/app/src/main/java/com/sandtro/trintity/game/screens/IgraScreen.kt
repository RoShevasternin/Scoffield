package com.sandtro.trintity.game.screens

import com.sandtro.trintity.game.actors.main.AMainIgra
import com.sandtro.trintity.game.utils.Block
import com.sandtro.trintity.game.utils.advanced.AdvancedMainScreen
import com.sandtro.trintity.game.utils.advanced.AdvancedStage
import com.sandtro.trintity.game.utils.gdxGame
import com.sandtro.trintity.game.utils.region

class IgraScreen: AdvancedMainScreen() {

    override val aMain = AMainIgra(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.GAMM)
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