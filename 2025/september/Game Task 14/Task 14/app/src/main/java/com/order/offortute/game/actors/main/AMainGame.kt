package com.order.offortute.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.order.offortute.game.actors.AOrder
import com.order.offortute.game.actors.ATimer
import com.order.offortute.game.actors.button.AButton
import com.order.offortute.game.screens.GameScreen
import com.order.offortute.game.screens.SettingsScreen
import com.order.offortute.game.screens.WinScreen
import com.order.offortute.game.utils.*
import com.order.offortute.game.utils.actor.animDelay
import com.order.offortute.game.utils.actor.animHide
import com.order.offortute.game.utils.actor.animShow
import com.order.offortute.game.utils.advanced.AdvancedMainGroup
import com.order.offortute.util.log

var GLOBAL_isWin = true
    private set

class AMainGame(override val screen: GameScreen): AdvancedMainGroup() {

    private val imgFrame  = Image(gdxGame.assetsAll.FRAME)
    private val btnMenu   = AButton(screen, AButton.Type.Menu)
    private val btnSett   = AButton(screen, AButton.Type.Sett)
    private val aTimer    = ATimer(screen)
    private val btnUpdate = AButton(screen, AButton.Type.Update)
    private val btnDone   = AButton(screen, AButton.Type.Done)
    private val aOrder    = AOrder(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgFrame()
        addBtnMenu()
        addATimer()
        addBtnREG()
        addAOrder()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgFrame() {
        addActor(imgFrame)
        imgFrame.setBounds(45f, 268f, 989f, 1384f)
    }

    private fun addBtnMenu() {
        addActor(btnMenu)
        btnMenu.setBounds(45f, 1728f, 132f, 132f)

        btnMenu.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }

        addActor(btnSett)
        btnSett.setBounds(902f, 1728f, 132f, 132f)

        btnSett.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(413f, 1616f, 254f, 254f)

        aTimer.timeOut = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(WinScreen::class.java.name)
            }
        }
        aTimer.start()
    }

    private fun addAOrder() {
        addActor(aOrder)
        aOrder.setBounds(276f, 519f, 528f, 797f)
    }

    private fun addBtnREG() {
        addActor(btnUpdate)
        btnUpdate.setBounds(246f, 52f, 192f, 192f)

        var isFirst = true
        btnUpdate.setOnClickListener {
            aOrder.update()
            if (isFirst) {
                isFirst = false
                btnDone.enable()
            }
        }

        addActor(btnDone)
        btnDone.setBounds(642f, 52f, 192f, 192f)
        btnDone.disable()

        btnDone.setOnClickListener {
            GLOBAL_isWin = aOrder.checkItems()
            log("f = $GLOBAL_isWin")

            screen.hideScreen {
                gdxGame.navigationManager.navigate(WinScreen::class.java.name)
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