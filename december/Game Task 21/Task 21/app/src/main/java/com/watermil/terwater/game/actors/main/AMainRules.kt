package com.watermil.terwater.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.watermil.terwater.game.actors.button.AButton
import com.watermil.terwater.game.actors.checkbox.ACheckBox
import com.watermil.terwater.game.screens.RulesScreen
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.TIME_ANIM_SCREEN
import com.watermil.terwater.game.utils.actor.animDelay
import com.watermil.terwater.game.utils.actor.animHide
import com.watermil.terwater.game.utils.actor.animShow
import com.watermil.terwater.game.utils.actor.setOnClickListener
import com.watermil.terwater.game.utils.advanced.AdvancedMainGroup
import com.watermil.terwater.game.utils.gdxGame

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules = Image(gdxGame.assetsAll.rules)
    private val btnX     = AButton(screen, AButton.Type.X)
    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnX()
        //addMus()
        //addSod()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(112f, 721f, 856f, 936f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(76f, 1696f, 120f, 120f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addMus() {
        addActor(mus)
        mus.setBounds(40f, 1761f, 100f, 100f)
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) mus.check()
        mus.setOnCheckListener {
            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }
    }

    private fun addSod() {
        addActor(snd)
        snd.setBounds(925f, 1761f, 100f, 100f)
        if (gdxGame.soundUtil.isPause) snd.check()
        snd.setOnCheckListener {
            gdxGame.soundUtil.isPause = it
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