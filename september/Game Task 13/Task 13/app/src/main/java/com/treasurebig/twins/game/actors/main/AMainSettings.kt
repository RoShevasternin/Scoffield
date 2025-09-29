package com.treasurebig.twins.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.treasurebig.twins.game.actors.button.AButton
import com.treasurebig.twins.game.actors.checkbox.ACheckBox
import com.treasurebig.twins.game.screens.SettingsScreen
import com.treasurebig.twins.game.utils.*
import com.treasurebig.twins.game.utils.actor.animDelay
import com.treasurebig.twins.game.utils.actor.animHide
import com.treasurebig.twins.game.utils.actor.animShow
import com.treasurebig.twins.game.utils.advanced.AdvancedMainGroup

class AMainSettings(override val screen: SettingsScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.SETTINS)
    private val btnX      = AButton(screen, AButton.Type.X)

    private val musicCB   = ACheckBox(screen, ACheckBox.Type.MUS)
    private val soundCB   = ACheckBox(screen, ACheckBox.Type.SOU)


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
        imgRules.setBounds(723f, 410f, 554f, 380f)
    }

    private fun addBtnS() {
        addActor(btnX)
        btnX.setBounds(1837f, 1028f, 122f, 122f)

        btnX.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }


    private fun addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(800f, 468f, 131f, 131f)
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
            setBounds(1068f, 468f, 131f, 131f)
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