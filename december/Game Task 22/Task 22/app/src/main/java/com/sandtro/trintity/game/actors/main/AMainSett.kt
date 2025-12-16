package com.sandtro.trintity.game.actors.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sandtro.trintity.game.actors.button.AButton
import com.sandtro.trintity.game.actors.checkbox.ACheckBox
import com.sandtro.trintity.game.screens.RulesScreen
import com.sandtro.trintity.game.screens.SettScreen
import com.sandtro.trintity.game.utils.Block
import com.sandtro.trintity.game.utils.TIME_ANIM_SCREEN
import com.sandtro.trintity.game.utils.actor.animDelay
import com.sandtro.trintity.game.utils.actor.animHide
import com.sandtro.trintity.game.utils.actor.animShow
import com.sandtro.trintity.game.utils.actor.setOnClickListener
import com.sandtro.trintity.game.utils.advanced.AdvancedMainGroup
import com.sandtro.trintity.game.utils.gdxGame
import com.sandtro.trintity.util.log

class AMainSett(override val screen: SettScreen): AdvancedMainGroup() {

    companion object {
        var isVib = true
            private set
    }

    private val imgRules = Image(gdxGame.assetsAll.SSS)
    private val btnX     = AButton(screen, AButton.Type.X)
    private val mus      = ACheckBox(screen, ACheckBox.Type.OnOff)
    private val snd      = ACheckBox(screen, ACheckBox.Type.OnOff)
    private val vib      = ACheckBox(screen, ACheckBox.Type.OnOff)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnX()
        addMus()
        addSod()
        addVib()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(275f, 551f, 531f, 1113f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(80f, 1750f, 90f, 90f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addMus() {
        addActor(mus)
        mus.setBounds(381f, 1145f, 318f, 142f)
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) mus.check()
        mus.setOnCheckListener {
            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }
    }

    private fun addSod() {
        addActor(snd)
        snd.setBounds(381f, 765f, 318f, 142f)
        if (gdxGame.soundUtil.isPause) snd.check()
        snd.setOnCheckListener {
            gdxGame.soundUtil.isPause = it
        }
    }

    private fun addVib() {
        addActor(vib)
        vib.setBounds(381f, 385f, 318f, 142f)
        if (isVib.not()) vib.check()
        vib.setOnCheckListener {
            isVib = it.not()
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}