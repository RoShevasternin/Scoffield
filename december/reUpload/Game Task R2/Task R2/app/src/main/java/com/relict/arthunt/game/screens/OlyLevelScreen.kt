package com.relict.arthunt.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relict.arthunt.game.LibGDXGame
import com.relict.arthunt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.relict.arthunt.game.utils.actor.animHide
import com.relict.arthunt.game.utils.actor.animShow
import com.relict.arthunt.game.utils.actor.setOnClickListener
import com.relict.arthunt.game.utils.advanced.AdvancedScreen
import com.relict.arthunt.game.utils.advanced.AdvancedStage
import com.relict.arthunt.game.utils.region

class OlyLevelScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var LEVEL = Static.Level.Easy
    }

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loadingAssets.BACKICH.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()
        addX()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addMenu() {
        val menu = Image(game.allAssets.OLY_LEVEL)
        addActor(menu)
        menu.setBounds(121f, 193f, 868f, 1651f)

        val levels = listOf(
            Static.Level.Easy,
            Static.Level.Normal,
            Static.Level.Hard,
        )

        var ny = 912f

        levels.onEach { level ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(210f, ny, 670f, 133f)
            ny -= 96f+133f

            btn.setOnClickListener(game.soundUtil) {
                LEVEL = level
                stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                    game.navigationManager.navigate(OlyGameScreen::class.java.name)
                }
            }
        }

    }

    private fun AdvancedStage.addX() {
        val xA = Actor()
        addActor(xA)
        xA.apply {
            setBounds(816f, 218f, 159f, 160f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    object Static {
        enum class Level {
            Easy, Normal, Hard
        }
    }


}