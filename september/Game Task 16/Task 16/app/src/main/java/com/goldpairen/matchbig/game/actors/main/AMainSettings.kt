package com.goldpairen.matchbig.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.goldpairen.matchbig.game.actors.button.AButton
import com.goldpairen.matchbig.game.actors.checkbox.ACheckBox
import com.goldpairen.matchbig.game.actors.progress.AProgress
import com.goldpairen.matchbig.game.screens.SettingsScreen
import com.goldpairen.matchbig.game.utils.*
import com.goldpairen.matchbig.game.utils.actor.animDelay
import com.goldpairen.matchbig.game.utils.actor.animHide
import com.goldpairen.matchbig.game.utils.actor.animShow
import com.goldpairen.matchbig.game.utils.actor.setOnClickListener
import com.goldpairen.matchbig.game.utils.advanced.AdvancedMainGroup
import kotlinx.coroutines.launch

class AMainSettings(override val screen: SettingsScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.SETT)
    private val btnX      = Actor() //AButton(screen, AButton.Type.X)

    private val musicCB   = AProgress(screen) //ACheckBox(screen, ACheckBox.Type.MUS)
    private val soundCB   = AProgress(screen) //ACheckBox(screen, ACheckBox.Type.SOU)


    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnS()

        addAProgressMusic()
        addAProgressSound()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(115f, 416f, 850f, 1087f)
    }

    private fun addBtnS() {
        addActor(btnX)
        btnX.setBounds(833f, 416f, 132f, 132f)

        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addAProgressMusic() {
        addActor(musicCB)
        musicCB.setBounds(228f, 1050f, 616f, 78f)

        musicCB.progressPercentFlow.value = gdxGame.musicUtil.volumeLevelFlow.value

        coroutine?.launch {
            musicCB.progressPercentFlow.collect { volume ->
                gdxGame.musicUtil.volumeLevelFlow.value = volume
            }
        }
    }

    private fun addAProgressSound() {
        addActor(soundCB)
        soundCB.setBounds(228f, 708f, 616f, 78f)

        soundCB.progressPercentFlow.value = gdxGame.soundUtil.volumeLevel

        coroutine?.launch {
            soundCB.progressPercentFlow.collect { volume ->
                gdxGame.soundUtil.volumeLevel = volume
            }
        }
    }


//    private fun addMusicCB() {
//        addActor(musicCB)
//        musicCB.apply {
//            setBounds(800f, 468f, 131f, 131f)
//            if (gdxGame.musicUtil.currentMusic?.isPlaying == false) check(false)
//
//            setOnCheckListener {
//                if (it) {
//                    gdxGame.musicUtil.currentMusic?.pause()
//                } else {
//                    gdxGame.musicUtil.currentMusic?.play()
//                }
//            }
//
//        }
//    }
//
//    private fun addSoundCB() {
//        addActor(soundCB)
//        soundCB.apply {
//            setBounds(1068f, 468f, 131f, 131f)
//            if (gdxGame.soundUtil.isPause) check(false)
//
//            setOnCheckListener { gdxGame.soundUtil.isPause = it }
//
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