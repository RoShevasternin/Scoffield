package com.appwin.fight.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.appwin.fight.game.actors.button.AButton
import com.appwin.fight.game.screens.PazzleScreen
import com.appwin.fight.game.screens.RulesScreen
import com.appwin.fight.game.screens.WinScreen
import com.appwin.fight.game.utils.*
import com.appwin.fight.game.utils.actor.animDelay
import com.appwin.fight.game.utils.actor.animHide
import com.appwin.fight.game.utils.actor.animShow
import com.appwin.fight.game.utils.actor.setBounds
import com.appwin.fight.game.utils.actor.setOrigin
import com.appwin.fight.game.utils.advanced.AdvancedMainGroup
import com.badlogic.gdx.utils.Align

class AMainWin(override val screen: WinScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.TEXT)
    private val imgSonce  = Image(gdxGame.assetsAll.SONCE)
    private val btnX      = AButton(screen, AButton.Type.X)
    private val btnNext   = AButton(screen, AButton.Type.Next)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnS()
        addBtnNext()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(73f, 1240f, 934f, 340f)

        addActor(imgSonce)
        imgSonce.setBounds(166f, 510f, 737f, 737f)
        imgSonce.addAction(Acts.forever(Acts.rotateBy(-360f, 5f)))

        imgSonce.setOrigin(Align.center)

        val img = Image(STAT_REG)
        addActor(img)
        img.setBounds(325f, 664f, 428f, 428f)
    }

    private fun addBtnS() {
        addActor(btnX)
        btnX.setBounds(71f, 1743f, 111f, 115f)

        btnX.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(264f, 293f, 552f, 167f)

        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(PazzleScreen::class.java.name)
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