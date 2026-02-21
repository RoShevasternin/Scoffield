package com.parrot.dicedash.game.screens

import com.parrot.dicedash.game.actors.button.AButton
import com.parrot.dicedash.game.utils.Block
import com.parrot.dicedash.game.utils.TIME_ANIM_SCREEN
import com.parrot.dicedash.game.utils.actor.animDelay
import com.parrot.dicedash.game.utils.actor.animHide
import com.parrot.dicedash.game.utils.actor.animShow
import com.parrot.dicedash.game.utils.advanced.AdvancedScreen
import com.parrot.dicedash.game.utils.advanced.AdvancedStage
import com.parrot.dicedash.game.utils.gdxGame

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
        btnMenu.setBounds(547f, 200f, 379f, 144f)
        btnMenu.setOnClickListener { animHide {
            gdxGame.navigationManager.clear()
            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
        } }
    }

    private fun AdvancedStage.addBtnRestart() {
        addActor(btnRestart)
        btnRestart.setBounds(152f, 200f, 379f, 144f)
        btnRestart.setOnClickListener {
            animHide {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name)
            }
        }
    }
}