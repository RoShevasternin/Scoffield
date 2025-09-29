package com.appwin.fight.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.appwin.fight.game.actors.button.AButton
import com.appwin.fight.game.actors.checkbox.ACheckBox
import com.appwin.fight.game.screens.RulesScreen
import com.appwin.fight.game.screens.SettingsScreen
import com.appwin.fight.game.utils.*
import com.appwin.fight.game.utils.actor.animDelay
import com.appwin.fight.game.utils.actor.animHide
import com.appwin.fight.game.utils.actor.animShow
import com.appwin.fight.game.utils.advanced.AdvancedMainGroup

class AMainSettings(override val screen: SettingsScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.sett)
    private val btnX      = AButton(screen, AButton.Type.X)

    private val musicCB   = ACheckBox(screen, ACheckBox.Type.ON_off)
    private val soundCB   = ACheckBox(screen, ACheckBox.Type.ON_off)


    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnS()
        addMusicCB()
        addSoundCB()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(146f, 808f, 787f, 646f)
    }

    private fun addBtnS() {
        addActor(btnX)
        btnX.setBounds(71f, 1743f, 111f, 115f)

        btnX.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }


    private fun addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(683f, 917f, 165f, 73f)
            if (gdxGame.musicUtil.currentMusic?.isPlaying == false) check(false)

            setOnCheckListener {
                if (it) {
                    gdxGame.musicUtil.currentMusic?.pause()
                } else {
                    gdxGame.musicUtil.currentMusic?.play()
                }
            }

        }
    }

    private fun addSoundCB() {
        addActor(soundCB)
        soundCB.apply {
            setBounds(320f, 917f, 165f, 73f)
            if (gdxGame.soundUtil.isPause) check(false)

            setOnCheckListener { gdxGame.soundUtil.isPause = it }

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