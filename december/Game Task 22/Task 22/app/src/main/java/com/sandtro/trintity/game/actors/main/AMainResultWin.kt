package com.sandtro.trintity.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sandtro.trintity.game.actors.button.AButton
import com.sandtro.trintity.game.screens.IgraScreen
import com.sandtro.trintity.game.screens.MenuScreen
import com.sandtro.trintity.game.screens.ResultWinScreen
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
import kotlinx.serialization.builtins.SetSerializer

class AMainResultWin(override val screen: ResultWinScreen): AdvancedMainGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.win)
    private val listBtn  = List(3) { Actor() }
    private val btnX     = AButton(screen, AButton.Type.X)

    override fun addActorsOnGroup() {
        color.a = 0f

        gdxGame.soundUtil.apply { play(win) }

        addImgPanel()
        addBtn()
        //addBtnX()

        val privacy = AButton(screen, AButton.Type.Privacy)
        addActor(privacy)
        privacy.setBounds(272f, 83f, 537f, 150f)
        privacy.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
        }

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(76f, 1696f, 120f, 120f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(272f, 687f, 537f, 806f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(272f, 1105f, 537f, 150f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(IgraScreen::class.java.name, MenuScreen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(272f, 896f, 537f, 150f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name)
                        }
                    }
                }
                2 -> {
                    image.setBounds(272f, 687f, 537f, 150f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(SettScreen::class.java.name, MenuScreen::class.java.name)
                        }
                    }
                }
            }
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