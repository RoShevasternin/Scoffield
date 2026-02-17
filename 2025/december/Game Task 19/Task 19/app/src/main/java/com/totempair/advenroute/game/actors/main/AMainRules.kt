package com.totempair.advenroute.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.totempair.advenroute.game.actors.button.AButton
import com.totempair.advenroute.game.actors.checkbox.ACheckBox
import com.totempair.advenroute.game.screens.RulesScreen
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.TIME_ANIM_SCREEN
import com.totempair.advenroute.game.utils.actor.animDelay
import com.totempair.advenroute.game.utils.actor.animHide
import com.totempair.advenroute.game.utils.actor.animShow
import com.totempair.advenroute.game.utils.actor.setOnClickListener
import com.totempair.advenroute.game.utils.advanced.AdvancedMainGroup
import com.totempair.advenroute.game.utils.gdxGame

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules = Image(gdxGame.assetsAll.rules)
    private val btnX     = AButton(screen, AButton.Type.X)
    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnX()
        addMus()
        addSod()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(191f, 645f, 751f, 629f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(801f, 1153f, 166f, 166f)
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