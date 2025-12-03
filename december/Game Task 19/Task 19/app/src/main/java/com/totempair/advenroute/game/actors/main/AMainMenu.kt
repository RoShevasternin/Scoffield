package com.totempair.advenroute.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.totempair.advenroute.game.actors.button.AButton
import com.totempair.advenroute.game.actors.checkbox.ACheckBox
import com.totempair.advenroute.game.screens.BackrScreen
import com.totempair.advenroute.game.screens.IgraScreen
import com.totempair.advenroute.game.screens.MenuScreen
import com.totempair.advenroute.game.screens.RulesScreen
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.TIME_ANIM_SCREEN
import com.totempair.advenroute.game.utils.actor.animDelay
import com.totempair.advenroute.game.utils.actor.animHide
import com.totempair.advenroute.game.utils.actor.animShow
import com.totempair.advenroute.game.utils.actor.setOnClickListener
import com.totempair.advenroute.game.utils.advanced.AdvancedMainGroup
import com.totempair.advenroute.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val imgMenu = Image(gdxGame.assetsAll.mantu)
    private val listBtn = List(2) { Actor() }
    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtn()
        addMus()
        addSod()

        val privacy = AButton(screen, AButton.Type.Select)
        addActor(privacy)
        privacy.setBounds(308f, 111f, 465f, 158f)
        privacy.setOnClickListener {
            gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
        }

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(205f, 690f, 647f, 546f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(297f, 982f, 464f, 156f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(BackrScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(297f, 786f, 464f, 156f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(RulesScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
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