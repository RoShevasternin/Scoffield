package com.secrofthe.lostpaircret.game.screens.template_screen

import com.badlogic.gdx.scenes.scene2d.Actor
import com.secrofthe.lostpaircret.game.LibGDXGame
import com.secrofthe.lostpaircret.game.screens.BParaScreen
import com.secrofthe.lostpaircret.game.screens.ExitScreen
import com.secrofthe.lostpaircret.game.screens.common.SettingsScreen
import com.secrofthe.lostpaircret.game.utils.TIME_ANIM_T
import com.secrofthe.lostpaircret.game.utils.actor.animHide
import com.secrofthe.lostpaircret.game.utils.actor.setOnClickListener
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedStage

class MenuScreen(_game: LibGDXGame): ISuperScreen(_game, Static.TypeScreen.Menu) {

    override fun AdvancedStage.addActorsOnStage() {
        var ny = 1071f

        listOf(
            BParaScreen::class.java.name,
            SettingsScreen::class.java.name,
            RulesScreen::class.java.name,
        ).onEach { scr ->
            addActor(Actor().apply {
                setBounds(315f, ny, 435f, 177f)
                ny -= 62f + 177f

                setOnClickListener(game.soundUtil) {
                    stageUI.root.animHide(TIME_ANIM_T) { game.navigationManager.navigate(scr, MenuScreen::class.java.name) }
                }
            })
        }

        xBlock = { game.navigationManager.navigate(ExitScreen::class.java.name) }

    }

}