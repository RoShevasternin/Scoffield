package com.watermil.terwater.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.watermil.terwater.game.actors.button.AButton
import com.watermil.terwater.game.actors.checkbox.ACheckBox
import com.watermil.terwater.game.screens.IgraScreen
import com.watermil.terwater.game.screens.MenuScreen
import com.watermil.terwater.game.screens.RulesScreen
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.TIME_ANIM_SCREEN
import com.watermil.terwater.game.utils.actor.animDelay
import com.watermil.terwater.game.utils.actor.animHide
import com.watermil.terwater.game.utils.actor.animShow
import com.watermil.terwater.game.utils.actor.setOnClickListener
import com.watermil.terwater.game.utils.advanced.AdvancedMainGroup
import com.watermil.terwater.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val imgMenu = Image(gdxGame.assetsAll.menu)
    private val listBtn = List(2) { Actor() }
    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtn()
        addMus()
        addSod()

        val privacy = Actor()
        addActor(privacy)
        privacy.setBounds(746f, 813f, 239f, 239f)
        privacy.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
        }

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(94f, 813f, 892f, 678f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(373f, 846f, 332f, 332f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(IgraScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(94f, 813f, 239f, 239f)
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