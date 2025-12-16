package com.icertif.pyrzzle.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.icertif.pyrzzle.game.LibGDXGame
import com.icertif.pyrzzle.game.utils.actor.setOnClickListener
import com.icertif.pyrzzle.game.utils.advanced.AdvancedScreen
import com.icertif.pyrzzle.game.utils.advanced.AdvancedStage
import com.icertif.pyrzzle.game.utils.region

class ResultScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var IS_WIN = true
    }

    override fun show() {
        if (IS_WIN) game.soundUtil.apply { play(game) } else game.soundUtil.apply { play(negative) }

        setBackgrounds(game.allAssets.bubinka.region, if (IS_WIN) game.allAssets.ggg.region else game.allAssets.rrr.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()

        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(866f, 1363f, 206f, 206f)
            setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

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