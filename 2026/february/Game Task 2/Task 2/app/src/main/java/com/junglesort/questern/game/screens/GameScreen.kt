package com.junglesort.questern.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.junglesort.questern.game.actors.AGamePan
import com.junglesort.questern.game.actors.AGamePanel
import com.junglesort.questern.game.actors.button.AImageButton
import com.junglesort.questern.game.utils.Block
import com.junglesort.questern.game.utils.TIME_ANIM_SCREEN
import com.junglesort.questern.game.utils.actor.addActorWithConstraints
import com.junglesort.questern.game.utils.actor.animDelay
import com.junglesort.questern.game.utils.actor.animHide
import com.junglesort.questern.game.utils.actor.animShow
import com.junglesort.questern.game.utils.advanced.AdvancedScreen
import com.junglesort.questern.game.utils.gdxGame

class GameScreen: AdvancedScreen() {

    private val btnBack   = AImageButton(this, AImageButton.Type.BACK)
    private val panel     = AGamePan(this)
    private val gamePanel = AGamePanel(this)

    override fun show() {
        AGamePanel.GLOBAL_COST_FLOW.value = 0
        
        stageUI.root.color.a = 0f
        setBackBackground(gdxGame.assetsLoader.BACKGROUND)
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        addBtnBack()
        addPanel()
        addGamePanel()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addBtnBack() {
        btnBack.setSize(179f, 179f)
        addActorWithConstraints(btnBack) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 71f
            marginTop   = 72f
        }

        btnBack.setOnClickListener {
            this@GameScreen.animHideScreen { gdxGame.navigationManager.back() }
        }

    }

    private fun Group.addPanel() {
        panel.setSize(746f, 291f)
        addActorWithConstraints(panel) {
            startToStartOf   = this@addPanel
            endToEndOf       = this@addPanel
            topToBottomOf    = btnBack

            marginTop = 47f
        }

        panel.timer.finishBlock = {
            animHideScreen { gdxGame.navigationManager.navigate(LoseScreen::class.java.name) }
        }
    }

    private fun Group.addGamePanel() {
        gamePanel.setSize(684f, 1070f)
        addActorWithConstraints(gamePanel) {
            startToStartOf   = this@addGamePanel
            endToEndOf       = this@addGamePanel
            topToBottomOf    = panel
            bottomToBottomOf = this@addGamePanel

            verticalBias = 0.75f
        }
    }

}