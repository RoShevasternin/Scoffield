package com.totempair.advenroute.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.totempair.advenroute.game.actors.ATimer
import com.totempair.advenroute.game.actors.button.AButton
import com.totempair.advenroute.game.screens.IgraScreen
import com.totempair.advenroute.game.screens.ResultWinScreen
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.TIME_ANIM_SCREEN
import com.totempair.advenroute.game.utils.actor.animDelay
import com.totempair.advenroute.game.utils.actor.animHide
import com.totempair.advenroute.game.utils.actor.animShow
import com.totempair.advenroute.game.utils.actor.setOnClickListener
import com.totempair.advenroute.game.utils.advanced.AdvancedMainGroup
import com.totempair.advenroute.game.utils.gdxGame

class AMainIgra(override val screen: IgraScreen): AdvancedMainGroup() {

    private val imgFind  = Image(gdxGame.assetsAll.find)
    private val imgPanel = Image(gdxGame.assetsAll.pan)
    private val btnX     = AButton(screen, AButton.Type.X)
    private val aTimer   = ATimer(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgFind()
        addBtnX()
        addImgPan()
        addATimer()
        addItems()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgFind() {
        addActor(imgFind)
        imgFind.setBounds(0f, 1557f, 1080f, 153f)
    }

    private fun addImgPan() {
        addActor(imgPanel)
        imgPanel.setBounds(378f, 141f, 324f, 120f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(63f, 1764f, 100f, 100f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(511f, 168f, 58f, 64f)
        aTimer.start()

        aTimer.timeOut = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultWinScreen::class.java.name)
            }
        }
    }

    private fun addItems() {
        when(GLOBAL_INDEX) {
            0 -> {

            }
            1 -> {

            }
            2 -> {

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