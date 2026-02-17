package com.sandtro.trintity.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sandtro.trintity.game.actors.button.AButton
import com.sandtro.trintity.game.actors.checkbox.ACheckBox
import com.sandtro.trintity.game.screens.IgraScreen
import com.sandtro.trintity.game.screens.MenuScreen
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

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val imgMenu = Image(gdxGame.assetsAll.mu)
    private val listBtn = List(3) { Actor() }
    //private val mus      = ACheckBox(screen, ACheckBox.Type.OnOff)
    //private val snd      = ACheckBox(screen, ACheckBox.Type.OnOff)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtn()
        //addMus()
        //addSod()

        val privacy = AButton(screen, AButton.Type.Privacy)
        addActor(privacy)
        privacy.setBounds(272f, 83f, 537f, 150f)
        privacy.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
        }

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(272f, 699f, 537f, 807f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(272f, 1117f, 537f, 149f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(IgraScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(272f, 908f, 537f, 149f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(RulesScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                2 -> {
                    image.setBounds(272f, 699f, 537f, 149f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(SettScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
            }
        }
    }


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