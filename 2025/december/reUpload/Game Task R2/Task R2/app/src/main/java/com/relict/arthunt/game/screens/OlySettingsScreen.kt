package com.relict.arthunt.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relict.arthunt.game.LibGDXGame
import com.relict.arthunt.game.actors.progress.AProgressValue
import com.relict.arthunt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.relict.arthunt.game.utils.actor.animHide
import com.relict.arthunt.game.utils.actor.animShow
import com.relict.arthunt.game.utils.actor.setOnClickListener
import com.relict.arthunt.game.utils.advanced.AdvancedScreen
import com.relict.arthunt.game.utils.advanced.AdvancedStage
import com.relict.arthunt.game.utils.region
import kotlinx.coroutines.launch

class OlySettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val musProg = AProgressValue(this)
    private val souProg = AProgressValue(this)

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
        val menu = Image(game.allAssets.OLY_SETTINGS)
        addActor(menu)
        menu.setBounds(121f, 193f, 868f, 1651f)

        addActors(musProg, souProg)
        musProg.apply {
            setBounds(237f, 856f, 633f, 70f)

            setProgressPercent(screen.game.musicUtil.volumeLevelFlow.value)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.musicUtil.volumeLevelFlow.value = it
                }
            }
        }
        souProg.apply {
            setBounds(237f, 577f, 633f, 70f)

            setProgressPercent(screen.game.soundUtil.volumeLevel)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.soundUtil.volumeLevel = it
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

}