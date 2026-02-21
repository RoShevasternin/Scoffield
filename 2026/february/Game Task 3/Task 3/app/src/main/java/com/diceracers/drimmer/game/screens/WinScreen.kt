package com.diceracers.drimmer.game.screens

import com.diceracers.drimmer.game.actors.button.AButton
import com.diceracers.drimmer.game.utils.Block
import com.diceracers.drimmer.game.utils.TIME_ANIM_SCREEN
import com.diceracers.drimmer.game.utils.actor.animDelay
import com.diceracers.drimmer.game.utils.actor.animHide
import com.diceracers.drimmer.game.utils.actor.animShow
import com.diceracers.drimmer.game.utils.advanced.AdvancedScreen
import com.diceracers.drimmer.game.utils.advanced.AdvancedStage
import com.diceracers.drimmer.game.utils.gdxGame

class WinScreen: AdvancedScreen() {

    private val btnMenu    = AButton(this, AButton.Type.MENU)
    private val btnRestart = AButton(this, AButton.Type.RESTART)

    override fun show() {
        gdxGame.ds_Record.update { it + 1 }
        gdxGame.soundUtil.apply { play(win) }

        setBackBackground(gdxGame.assetsAll.WIN)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        root.color.a = 0f

        addBtnMenu()
        addBtnRestart()

        animShow()
    }

    override fun animShow(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addBtnMenu() {
        addActor(btnMenu)
        btnMenu.setBounds(559f, 219f, 342f, 115f)
        btnMenu.setOnClickListener { animHide {
            gdxGame.navigationManager.clear()
            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
        } }
    }

    private fun AdvancedStage.addBtnRestart() {
        addActor(btnRestart)
        btnRestart.setBounds(178f, 219f, 342f, 115f)
        btnRestart.setOnClickListener {
            animHide {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name)
            }
        }
    }
}