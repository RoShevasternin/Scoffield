package com.tikispit.ilets.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tikispit.ilets.game.actors.button.AButton
import com.tikispit.ilets.game.actors.checkbox.ACheckBox
import com.tikispit.ilets.game.screens.PuzzleScreen
import com.tikispit.ilets.game.screens.MenuScreen
import com.tikispit.ilets.game.screens.RulesScreen
import com.tikispit.ilets.game.utils.Block
import com.tikispit.ilets.game.utils.TIME_ANIM_SCREEN
import com.tikispit.ilets.game.utils.actor.animDelay
import com.tikispit.ilets.game.utils.actor.animHide
import com.tikispit.ilets.game.utils.actor.animShow
import com.tikispit.ilets.game.utils.actor.setOnClickListener
import com.tikispit.ilets.game.utils.advanced.AdvancedMainGroup
import com.tikispit.ilets.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val imgMenu = Image(gdxGame.assetsAll.palun)
    private val listBtn = List(2) { Actor() }
    private val mus     = ACheckBox(screen, ACheckBox.Type.Mus)
    private val snd     = ACheckBox(screen, ACheckBox.Type.Sod)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtn()
        addMus()
        addSod()

        val privacy = Actor()
        addActor(privacy)
        privacy.setBounds(277f, 555f, 526f, 218f)
        privacy.setOnClickListener {
            gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
        }

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(224f, 555f, 633f, 809f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(224f, 1033f, 632f, 330f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(PuzzleScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(277f, 789f, 526f, 218f)
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
        mus.setBounds(917f, 1753f, 105f, 95f)
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) mus.check()
        mus.setOnCheckListener {
            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }
    }

    private fun addSod() {
        addActor(snd)
        snd.setBounds(58f, 1753f, 105f, 95f)
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