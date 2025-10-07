package com.fortunepieces.goldrushbig.game.screens

import com.fortunepieces.goldrushbig.game.actors.main.AMainPuzlaks
import com.fortunepieces.goldrushbig.game.utils.Block
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedMainScreen
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedStage
import com.fortunepieces.goldrushbig.game.utils.gdxGame
import com.fortunepieces.goldrushbig.game.utils.region

class PazzleScreen: AdvancedMainScreen() {

    override val aMain = AMainPuzlaks(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKGRANDE.region)
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