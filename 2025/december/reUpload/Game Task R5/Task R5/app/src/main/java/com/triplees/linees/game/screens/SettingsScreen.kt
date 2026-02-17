package com.triplees.linees.game.screens

import com.triplees.linees.game.LibGDXGame
import com.triplees.linees.game.actors.ASettings
import com.triplees.linees.game.utils.advanced.AdvancedStage

class SettingsScreen(ame: LibGDXGame) : IPanelScreen(ame, ScreenType.SETTINGS) {

    private val settings = ASettings(this)

    override fun AdvancedStage.addActorsOnStage() {
        addSetting()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addSetting() {
        addActor(settings)
        settings.setBounds(242f, 733f, 607f, 328f)
    }

}