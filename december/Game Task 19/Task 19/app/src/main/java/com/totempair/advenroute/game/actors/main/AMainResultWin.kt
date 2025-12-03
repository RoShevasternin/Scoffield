package com.totempair.advenroute.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.totempair.advenroute.game.screens.IgraScreen
import com.totempair.advenroute.game.screens.MenuScreen
import com.totempair.advenroute.game.screens.ResultWinScreen
import com.totempair.advenroute.game.screens.RulesScreen
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.TIME_ANIM_SCREEN
import com.totempair.advenroute.game.utils.actor.animDelay
import com.totempair.advenroute.game.utils.actor.animHide
import com.totempair.advenroute.game.utils.actor.animShow
import com.totempair.advenroute.game.utils.actor.setOnClickListener
import com.totempair.advenroute.game.utils.advanced.AdvancedMainGroup
import com.totempair.advenroute.game.utils.gdxGame

class AMainResultWin(override val screen: ResultWinScreen): AdvancedMainGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.WELL)
    private val listBtn  = List(2) { Actor() }

    override fun addActorsOnGroup() {
        color.a = 0f

        gdxGame.soundUtil.apply { play(win) }

        addImgPanel()
        addBtn()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(134f, 687f, 806f, 748f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(309f, 979f, 464f, 156f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(309f, 783f, 464f, 156f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(RulesScreen::class.java.name)
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