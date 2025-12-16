package com.tikispit.ilets.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tikispit.ilets.game.actors.button.AButton
import com.tikispit.ilets.game.screens.MenuScreen
import com.tikispit.ilets.game.screens.PuzzleScreen
import com.tikispit.ilets.game.screens.ResultGREENScreen
import com.tikispit.ilets.game.screens.RulesScreen
import com.tikispit.ilets.game.utils.Block
import com.tikispit.ilets.game.utils.TIME_ANIM_SCREEN
import com.tikispit.ilets.game.utils.actor.animDelay
import com.tikispit.ilets.game.utils.actor.animHide
import com.tikispit.ilets.game.utils.actor.animShow
import com.tikispit.ilets.game.utils.actor.setOnClickListener
import com.tikispit.ilets.game.utils.advanced.AdvancedMainGroup
import com.tikispit.ilets.game.utils.gdxGame

class AMainResultGREEN(override val screen: ResultGREENScreen): AdvancedMainGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.VIC)
    private val listBtn  = List(2) { Actor() }
    private val btnBack  = AButton(screen, AButton.Type.Back)

    override fun addActorsOnGroup() {
        color.a = 0f

        gdxGame.soundUtil.apply { play(win) }

        addImgPanel()
        addBtn()
        addBtnBack()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(58f, 1755f, 107f, 92f)

        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(70f, 490f, 937f, 1450f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(224f, 734f, 632f, 330f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            //gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(PuzzleScreen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(277f, 490f, 527f, 218f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        //screen.hideScreen {
                            //gdxGame.navigationManager.clearBackStack()
                            //gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name)
                            gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
                        //}
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