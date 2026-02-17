package com.fortunepieces.goldrushbig.game.screens

import com.fortunepieces.goldrushbig.game.actors.main.AMainFail
import com.fortunepieces.goldrushbig.game.actors.main.AMainWin
import com.fortunepieces.goldrushbig.game.utils.Block
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedMainScreen
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedStage
import com.fortunepieces.goldrushbig.game.utils.gdxGame
import com.fortunepieces.goldrushbig.game.utils.region

class FailScreen: AdvancedMainScreen() {

    override val aMain = AMainFail(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.LOSE.region)
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