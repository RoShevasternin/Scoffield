package com.tikispit.ilets.game.screens

import com.tikispit.ilets.game.actors.main.AMainResultGREEN
import com.tikispit.ilets.game.utils.Block
import com.tikispit.ilets.game.utils.advanced.AdvancedMainScreen
import com.tikispit.ilets.game.utils.advanced.AdvancedStage
import com.tikispit.ilets.game.utils.gdxGame
import com.tikispit.ilets.game.utils.region

class ResultGREENScreen: AdvancedMainScreen() {

    override val aMain = AMainResultGREEN(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.GREN.region)
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