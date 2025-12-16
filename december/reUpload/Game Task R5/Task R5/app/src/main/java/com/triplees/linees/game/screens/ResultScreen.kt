package com.triplees.linees.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.triplees.linees.game.LibGDXGame
import com.triplees.linees.game.screens.level.Level_1_Screen
import com.triplees.linees.game.screens.level.Level_2_Screen
import com.triplees.linees.game.utils.TIME_ANIM
import com.triplees.linees.game.utils.actor.animHide
import com.triplees.linees.game.utils.actor.setOnClickListener
import com.triplees.linees.game.utils.advanced.AdvancedScreen
import com.triplees.linees.game.utils.advanced.AdvancedStage
import com.triplees.linees.game.utils.region

class ResultScreen(override val game: LibGDXGame) : AdvancedScreen() {
    companion object {
        var isWin = true
    }

    private val panelImg = Image(if (isWin) game.allAssets.you_win else game.allAssets.you_lose)
    private val xActor   = Actor()

    override fun show() {
        setBackBackground(if (isWin) game.allAssets.Win.region else game.allAssets.Fail.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addBtns()
        addXActor()

        if (isWin) game.soundUtil.apply { play(WIN) } else game.soundUtil.apply { play(LOSE) }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(147f, 507f, 797f, 873f)
    }


    private fun AdvancedStage.addBtns() {
        val listLevelScreenName = listOf(
            Level_1_Screen::class.java.name,
            Level_2_Screen::class.java.name,
        )

        val names = listOf(
            listLevelScreenName.random(),
            SettingsScreen::class.java.name,
            RulesScreen::class.java.name,
        )

        var ny = 988f

        names.onEach { screenName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(334f, ny, 430f, 127f)
            ny -= 49f + 127f

            btn.setOnClickListener(game.soundUtil) { navToByScreenName(screenName) }
        }
    }

    private fun AdvancedStage.addXActor() {
        addActor(xActor)
        xActor.setBounds(813f, 507f, 130f, 133f)
        xActor.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navToByScreenName(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}