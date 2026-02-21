package com.junglesort.questern.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.junglesort.questern.game.actors.button.AButton
import com.junglesort.questern.game.utils.Block
import com.junglesort.questern.game.utils.TIME_ANIM_SCREEN
import com.junglesort.questern.game.utils.actor.addActorWithConstraints
import com.junglesort.questern.game.utils.actor.animDelay
import com.junglesort.questern.game.utils.actor.animHide
import com.junglesort.questern.game.utils.actor.animShow
import com.junglesort.questern.game.utils.advanced.AdvancedScreen
import com.junglesort.questern.game.utils.gdxGame
import com.junglesort.questern.util.log

class WelcomeScreen: AdvancedScreen() {

    private val imgWelcome = Image(gdxGame.assetsAll.WELCOME_PAN)
    private val btnPlay    = AButton(this, AButton.Type.NEXT)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addImgWelcome()
        addBtnPlay()

        animShow()
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

    private fun Group.addImgWelcome() {
        imgWelcome.setSize(990f, 1290f)
        addActorWithConstraints(imgWelcome) {
            startToStartOf   = this@addImgWelcome
            endToEndOf       = this@addImgWelcome
            topToTopOf       = this@addImgWelcome
            bottomToBottomOf = this@addImgWelcome

            verticalBias = 0.7f
        }

        log("a = ${imgWelcome.x} | ${imgWelcome.y}")
    }

    private fun Group.addBtnPlay() {
        btnPlay.setSize(509f, 144f)
        addActorWithConstraints(btnPlay) {
            startToStartOf   = imgWelcome
            endToEndOf       = imgWelcome
            topToBottomOf    = imgWelcome
            bottomToBottomOf = this@addBtnPlay

            verticalBias = 0.7f
        }

        btnPlay.setOnClickListener {
            this@WelcomeScreen.animHideScreen { gdxGame.navigationManager.navigate(MenuScreen::class.java.name, this::class.java.name) }
        }

    }

}