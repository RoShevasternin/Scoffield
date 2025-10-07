package com.puzfortunes.twinst.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzfortunes.twinst.game.actors.button.AButton
import com.puzfortunes.twinst.game.screens.PazzleScreen
import com.puzfortunes.twinst.game.screens.RulesScreen
import com.puzfortunes.twinst.game.screens.WinScreen
import com.puzfortunes.twinst.game.utils.*
import com.puzfortunes.twinst.game.utils.actor.animDelay
import com.puzfortunes.twinst.game.utils.actor.animHide
import com.puzfortunes.twinst.game.utils.actor.animShow
import com.puzfortunes.twinst.game.utils.actor.setBounds
import com.puzfortunes.twinst.game.utils.actor.setOrigin
import com.puzfortunes.twinst.game.utils.advanced.AdvancedMainGroup
import com.badlogic.gdx.utils.Align
import com.puzfortunes.twinst.game.utils.actor.setOnClickListener

class AMainWin(override val screen: WinScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.WINES)
    private val btnX      = Actor() //AButton(screen, AButton.Type.X)
    private val btnNext   = Actor() //AButton(screen, AButton.Type.X)

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
        imgRules.setBounds(119f, 610f, 889f, 699f)
    }

    private fun addBtnS() {
        addActor(btnX)
        btnX.setBounds(867f, 1168f, 141f, 141f)

        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(206f, 727f, 668f, 232f)

        btnNext.setOnClickListener(gdxGame.soundUtil) {
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