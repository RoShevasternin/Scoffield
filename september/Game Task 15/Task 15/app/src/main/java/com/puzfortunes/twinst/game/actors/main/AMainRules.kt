package com.puzfortunes.twinst.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzfortunes.twinst.game.actors.button.AButton
import com.puzfortunes.twinst.game.actors.checkbox.ACheckBox
import com.puzfortunes.twinst.game.screens.RulesScreen
import com.puzfortunes.twinst.game.utils.*
import com.puzfortunes.twinst.game.utils.actor.animDelay
import com.puzfortunes.twinst.game.utils.actor.animHide
import com.puzfortunes.twinst.game.utils.actor.animShow
import com.puzfortunes.twinst.game.utils.actor.setOnClickListener
import com.puzfortunes.twinst.game.utils.advanced.AdvancedMainGroup

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.RULES)
    private val btnX      = Actor() //AButton(screen, AButton.Type.X)

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
        imgRules.setBounds(118f, 709f, 889f, 699f)
    }

    private fun addBtnS() {
        addActor(btnX)
        btnX.setBounds(866f, 1267f, 141f, 141f)

        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(891f, 1723f, 141f, 141f)
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
            setBounds(48f, 1723f, 141f, 141f)
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