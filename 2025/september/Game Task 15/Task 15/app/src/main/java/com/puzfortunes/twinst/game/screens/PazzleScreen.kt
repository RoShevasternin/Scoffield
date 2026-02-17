package com.puzfortunes.twinst.game.screens

import com.puzfortunes.twinst.game.actors.main.AMainPuzlaks
import com.puzfortunes.twinst.game.utils.Block
import com.puzfortunes.twinst.game.utils.advanced.AdvancedMainScreen
import com.puzfortunes.twinst.game.utils.advanced.AdvancedStage
import com.puzfortunes.twinst.game.utils.gdxGame
import com.puzfortunes.twinst.game.utils.region

class PazzleScreen: AdvancedMainScreen() {

    override val aMain = AMainPuzlaks(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACK.region)
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