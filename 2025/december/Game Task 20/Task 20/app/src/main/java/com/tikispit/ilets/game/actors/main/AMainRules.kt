package com.tikispit.ilets.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tikispit.ilets.game.actors.button.AButton
import com.tikispit.ilets.game.actors.checkbox.ACheckBox
import com.tikispit.ilets.game.screens.RulesScreen
import com.tikispit.ilets.game.utils.Block
import com.tikispit.ilets.game.utils.TIME_ANIM_SCREEN
import com.tikispit.ilets.game.utils.actor.animDelay
import com.tikispit.ilets.game.utils.actor.animHide
import com.tikispit.ilets.game.utils.actor.animShow
import com.tikispit.ilets.game.utils.actor.setOnClickListener
import com.tikispit.ilets.game.utils.advanced.AdvancedMainGroup
import com.tikispit.ilets.game.utils.gdxGame

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules = Image(gdxGame.assetsAll.RUSEL)
    private val btnX     = AButton(screen, AButton.Type.Back)
//    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
//    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnX()
//        addMus()
//        addSod()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(76f, 478f, 928f, 963f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(58f, 1755f, 108f, 93f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }
//
//    private fun addMus() {
//        addActor(mus)
//        mus.setBounds(40f, 1761f, 100f, 100f)
//        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) mus.check()
//        mus.setOnCheckListener {
//            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
//        }
//    }
//
//    private fun addSod() {
//        addActor(snd)
//        snd.setBounds(925f, 1761f, 100f, 100f)
//        if (gdxGame.soundUtil.isPause) snd.check()
//        snd.setOnCheckListener {
//            gdxGame.soundUtil.isPause = it
//        }
//    }

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