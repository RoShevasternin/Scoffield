package com.parrot.dicedash.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.parrot.dicedash.game.actors.button.AButton
import com.parrot.dicedash.game.utils.Block
import com.parrot.dicedash.game.utils.TIME_ANIM_SCREEN
import com.parrot.dicedash.game.utils.actor.animDelay
import com.parrot.dicedash.game.utils.actor.animHide
import com.parrot.dicedash.game.utils.actor.animShow
import com.parrot.dicedash.game.utils.advanced.AdvancedScreen
import com.parrot.dicedash.game.utils.advanced.AdvancedStage
import com.parrot.dicedash.game.utils.gdxGame

class HTPScreen: AdvancedScreen() {

    private val listHTPTexture = listOf(
        gdxGame.assetsAll.HTP_1,
        gdxGame.assetsAll.HTP_2,
    )
    private var currentIndex = 0
        set(value) {
            field = value
            updateImgHTP()
        }

    private val btnNext = AButton(this, AButton.Type.NEXT)
    private val btnMenu = AButton(this, AButton.Type.MENU)
    private val imgHTP  = Image(listHTPTexture[currentIndex])

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        root.color.a = 0f

        addBtnNext()
        addBtnMenu()
        addImgHTP()

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

    private fun AdvancedStage.addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(350f, 282f, 380f, 146f)
        btnNext.setOnClickListener {
            currentIndex = if (currentIndex + 1 >= 2) 0 else currentIndex + 1
        }
    }

    private fun AdvancedStage.addBtnMenu() {
        addActor(btnMenu)
        btnMenu.setBounds(350f, 121f, 380f, 145f)
        btnMenu.setOnClickListener { animHide { gdxGame.navigationManager.back() } }
    }

    private fun AdvancedStage.addImgHTP() {
        addActor(imgHTP)
        imgHTP.setBounds(148f, 601f, 785f, 1093f)
    }

    // Logic ------------------------------------------------------------------------

    private fun updateImgHTP() {
        imgHTP.drawable = TextureRegionDrawable(listHTPTexture[currentIndex])
    }
}