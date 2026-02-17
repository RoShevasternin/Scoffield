package com.icertif.pyrzzle.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.icertif.pyrzzle.game.LibGDXGame
import com.icertif.pyrzzle.game.utils.actor.setOnClickListener
import com.icertif.pyrzzle.game.utils.advanced.AdvancedScreen
import com.icertif.pyrzzle.game.utils.advanced.AdvancedStage
import com.icertif.pyrzzle.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val panelImg = Image(game.allAssets.menu)

    override fun show() {
        setBackBackground(game.allAssets.bubinka.region)
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
        panelImg.setBounds(21f, 408f, 1051f, 1380f)

        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(866f, 1363f, 206f, 206f)
            setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
        }
    }

    private fun AdvancedStage.addMenu() {
        val selections = listOf(
            LevelScreen   ::class.java.name,
            SettingsScreen::class.java.name,
            RulesScreen   ::class.java.name,
        )

        var ny = 1173f
        selections.onEach { screenName ->
            addActor(Actor().apply {
                setBounds(250f, ny, 593f, 206f)
                ny -= 59f+206f
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