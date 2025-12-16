package com.piriamidone.twispinton.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.piriamidone.twispinton.game.LibGDXGame
import com.piriamidone.twispinton.game.utils.actor.setOnClickListener
import com.piriamidone.twispinton.game.utils.advanced.AdvancedScreen
import com.piriamidone.twispinton.game.utils.advanced.AdvancedStage
import com.piriamidone.twispinton.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val panelImg = Image(game.alllAssets.phara_panel)
    private val menuImg  = Image(game.alllAssets.menu)


    override fun show() {
        setBackgrounds(game.loadAssets.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelImg()
        addMenu()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.setBounds(0f, 0f, 966f, 1603f)

        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(730f, 341f, 171f, 175f)
            setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(menuImg)
        menuImg.setBounds(294f, 819f, 494f, 933f)

        val selections = listOf(
            LevelScreen   ::class.java.name,
            SettingsScreen::class.java.name,
            RulesScreen   ::class.java.name,
        )

        var ny = 1247f
        selections.onEach { screenName ->
            addActor(Actor().apply {
                setBounds(294f, ny, 494f, 158f)
                ny -= 56f+158f
                setOnClickListener(game.soundUtil) { navigateGo(screenName) }
            })
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navigateGo(id: String) {
        animHideScreen { game.navigationManager.navigate(id, this::class.java.name) }
    }


}