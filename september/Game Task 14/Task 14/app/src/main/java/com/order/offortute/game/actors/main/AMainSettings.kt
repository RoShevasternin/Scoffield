package com.order.offortute.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.order.offortute.game.actors.button.AButton
import com.order.offortute.game.actors.checkbox.ACheckBox
import com.order.offortute.game.screens.SettingsScreen
import com.order.offortute.game.utils.*
import com.order.offortute.game.utils.actor.animDelay
import com.order.offortute.game.utils.actor.animHide
import com.order.offortute.game.utils.actor.animShow
import com.order.offortute.game.utils.advanced.AdvancedMainGroup

class AMainSettings(override val screen: SettingsScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.SETTINGS)
    private val btnMenu   = AButton(screen, AButton.Type.Menu)
    private val imgFrame  = Image(gdxGame.assetsAll.FRAME)

    private val musicCB   = ACheckBox(screen, ACheckBox.Type.ON_OFF)
    private val soundCB   = ACheckBox(screen, ACheckBox.Type.ON_OFF)


    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnMenu()
        addMusicCB()
        addSoundCB()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgFrame)
        imgFrame.setBounds(45f, 268f, 989f, 1384f)

        addActor(imgRules)
        imgRules.setBounds(261f, 778f, 558f, 976f)
    }

    private fun addBtnMenu() {
        addActor(btnMenu)
        btnMenu.setBounds(45f, 1728f, 132f, 132f)

        btnMenu.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }


    private fun addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(454f, 1067f, 172f, 172f)
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
            setBounds(454f, 606f, 172f, 172f)
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