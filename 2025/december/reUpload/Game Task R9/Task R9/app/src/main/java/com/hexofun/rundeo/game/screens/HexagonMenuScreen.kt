package com.hexofun.rundeo.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.hexofun.rundeo.game.LibGDXGame
import com.hexofun.rundeo.game.utils.TIME_ANIM
import com.hexofun.rundeo.game.utils.actor.animHide
import com.hexofun.rundeo.game.utils.actor.animShow
import com.hexofun.rundeo.game.utils.actor.setOnClickListener
import com.hexofun.rundeo.game.utils.advanced.AdvancedScreen
import com.hexofun.rundeo.game.utils.advanced.AdvancedStage
import com.hexofun.rundeo.game.utils.region

class HexagonMenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val panelImg = Image(game.allAssets.menu)

    override fun show() {
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.startAssets.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addButtons()
        addExit()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(519f, 234f, 882f, 726f)
    }

    private fun AdvancedStage.addButtons() {
        var ny = 355f
        arrayOf(
            HexagonRulesScreen::class.java.name,
            HexagonSettingsScreen::class.java.name,
            HexagonMapScreen::class.java.name,
        ).onEach { sName ->
            addActor(Actor().apply {
                setBounds(751f, ny, 430f, 105f)
                ny += (30+105)

                setOnClickListener(game.soundUtil) {
                    stageUI.root.animHide(TIME_ANIM) {
                        game.navigationManager.navigate(sName, HexagonMenuScreen::class.java.name)
                    }
                }
            })
        }
    }

    private fun AdvancedStage.addExit() {
        val exit = Actor()
        addActors(exit)
        exit.setBounds(1227f, 234f, 130f, 133f)
        exit.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM) { game.navigationManager.exit() }
        }
    }

}