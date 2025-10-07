package com.fortunepieces.goldrushbig.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fortunepieces.goldrushbig.game.actors.ABtns
import com.fortunepieces.goldrushbig.game.screens.MenuScreen
import com.fortunepieces.goldrushbig.game.screens.PazzleScreen
import com.fortunepieces.goldrushbig.game.screens.WinScreen
import com.fortunepieces.goldrushbig.game.utils.Block
import com.fortunepieces.goldrushbig.game.utils.TIME_ANIM_SCREEN
import com.fortunepieces.goldrushbig.game.utils.actor.animDelay
import com.fortunepieces.goldrushbig.game.utils.actor.animHide
import com.fortunepieces.goldrushbig.game.utils.actor.animShow
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedMainGroup
import com.fortunepieces.goldrushbig.game.utils.gdxGame

class AMainWin(override val screen: WinScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.WINER)
    private val aBtns    = ABtns(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnS()

        animShowMain()

        gdxGame.soundUtil.apply { play(win) }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(633f, 408f, 654f, 405f)
    }

    private fun addBtnS() {
        addActor(aBtns)
        aBtns.setBounds(638f, 126f, 645f, 173f)

        aBtns.playBlock = { gdxGame.navigationManager.back() }
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