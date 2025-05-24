package com.plinkray.ioosudmarine.game.screens

import com.plinkray.ioosudmarine.game.actors.main.AMainRules
import com.plinkray.ioosudmarine.game.utils.Block
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedMainScreen
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedStage
import com.plinkray.ioosudmarine.game.utils.gdxGame
import com.plinkray.ioosudmarine.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
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