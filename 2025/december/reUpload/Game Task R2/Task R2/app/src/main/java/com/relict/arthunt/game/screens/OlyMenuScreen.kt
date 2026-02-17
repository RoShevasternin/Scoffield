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

class OlyMenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

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
        val menu = Image(game.allAssets.OLY_MENU)
        addActor(menu)
        menu.setBounds(121f, 193f, 868f, 1651f)

        val names = listOf(
            OlyLevelScreen::class.java.name,
            OlySettingsScreen::class.java.name,
            OlyRulesScreen::class.java.name,
        )

        var ny = 912f

        names.onEach { sName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(210f, ny, 670f, 133f)
            ny -= 96f+133f

            btn.setOnClickListener(game.soundUtil) { navigateGo(sName) }
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

    private fun navigateGo(sName: String) {
        stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
            game.navigationManager.navigate(sName, OlyMenuScreen::class.java.name)
        }
    }


}