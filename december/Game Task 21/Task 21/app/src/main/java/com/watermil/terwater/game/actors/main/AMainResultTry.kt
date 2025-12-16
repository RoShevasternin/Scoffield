package com.watermil.terwater.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.watermil.terwater.game.actors.button.AButton
import com.watermil.terwater.game.screens.IgraScreen
import com.watermil.terwater.game.screens.MenuScreen
import com.watermil.terwater.game.screens.ResultTryScreen
import com.watermil.terwater.game.screens.ResultWinScreen
import com.watermil.terwater.game.screens.RulesScreen
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.TIME_ANIM_SCREEN
import com.watermil.terwater.game.utils.actor.animDelay
import com.watermil.terwater.game.utils.actor.animHide
import com.watermil.terwater.game.utils.actor.animShow
import com.watermil.terwater.game.utils.actor.setOnClickListener
import com.watermil.terwater.game.utils.advanced.AdvancedMainGroup
import com.watermil.terwater.game.utils.gdxGame

class AMainResultTry(override val screen: ResultTryScreen): AdvancedMainGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.project)
    private val listBtn  = List(3) { Actor() }
    private val btnX     = AButton(screen, AButton.Type.X)

    override fun addActorsOnGroup() {
        color.a = 0f

        gdxGame.soundUtil.apply { play(fail) }

        addImgPanel()
        addBtn()
        addBtnX()

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
        imgPanel.setBounds(109f, 783f, 862f, 650f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(379f, 815f, 321f, 321f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            //gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(IgraScreen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(109f, 783f, 231f, 231f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            //gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(RulesScreen::class.java.name)
                        }
                    }
                }
                2 -> {
                    image.setBounds(739f, 783f, 231f, 231f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
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