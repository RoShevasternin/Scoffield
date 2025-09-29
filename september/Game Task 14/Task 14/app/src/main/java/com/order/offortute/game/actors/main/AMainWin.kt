package com.order.offortute.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.order.offortute.game.actors.button.AButton
import com.order.offortute.game.screens.WinScreen
import com.order.offortute.game.utils.*
import com.order.offortute.game.utils.actor.animDelay
import com.order.offortute.game.utils.actor.animHide
import com.order.offortute.game.utils.actor.animShow
import com.order.offortute.game.utils.advanced.AdvancedMainGroup
import com.order.offortute.game.screens.SettingsScreen

class AMainWin(override val screen: WinScreen): AdvancedMainGroup() {

    private val btnMenu   = AButton(screen, AButton.Type.Menu)
    private val btnSett   = AButton(screen, AButton.Type.Sett)
    private val imgResult = Image(if (GLOBAL_isWin) gdxGame.assetsAll.WIN else gdxGame.assetsAll.LOSE)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnMenu()

        animShowMain()

        gdxGame.soundUtil.apply { if (GLOBAL_isWin) play(win) else play(fail) }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgResult)
        imgResult.setBounds(223f, 641f, 635f, 635f)
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
                gdxGame.navigationManager.navigate(SettingsScreen::class.java.name)
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