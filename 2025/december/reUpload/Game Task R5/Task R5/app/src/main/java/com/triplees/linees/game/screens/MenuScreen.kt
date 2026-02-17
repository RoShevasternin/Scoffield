package com.triplees.linees.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.triplees.linees.game.LibGDXGame
import com.triplees.linees.game.screens.level.Level_1_Screen
import com.triplees.linees.game.screens.level.Level_2_Screen
import com.triplees.linees.game.utils.TIME_ANIM
import com.triplees.linees.game.utils.actor.animHide
import com.triplees.linees.game.utils.actor.setOnClickListener
import com.triplees.linees.game.utils.advanced.AdvancedStage

class MenuScreen(ame: LibGDXGame) : IPanelScreen(ame, ScreenType.MENU) {

    override fun AdvancedStage.addActorsOnStage() {
        addBtns()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navToByScreenName(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName, MenuScreen::class.java.name) }
    }


}