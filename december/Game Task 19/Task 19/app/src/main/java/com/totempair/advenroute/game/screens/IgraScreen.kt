package com.totempair.advenroute.game.screens

import com.totempair.advenroute.game.actors.main.AMainIgra
import com.totempair.advenroute.game.actors.main.AMainRules
import com.totempair.advenroute.game.actors.main.GLOBAL_BACKG
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.advanced.AdvancedMainScreen
import com.totempair.advenroute.game.utils.advanced.AdvancedStage
import com.totempair.advenroute.game.utils.gdxGame
import com.totempair.advenroute.game.utils.region

class IgraScreen: AdvancedMainScreen() {

    override val aMain = AMainIgra(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(GLOBAL_BACKG!!.region)
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