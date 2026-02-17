package com.fivematch.fivemaster.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fivematch.fivemaster.game.actors.button.AButton
import com.fivematch.fivemaster.game.screens.ResultScreen
import com.fivematch.fivemaster.game.screens.RulesScreen
import com.fivematch.fivemaster.game.utils.Block
import com.fivematch.fivemaster.game.utils.TIME_ANIM_SCREEN
import com.fivematch.fivemaster.game.utils.actor.animDelay
import com.fivematch.fivemaster.game.utils.actor.animHide
import com.fivematch.fivemaster.game.utils.actor.animShow
import com.fivematch.fivemaster.game.utils.actor.setOnClickListener
import com.fivematch.fivemaster.game.utils.advanced.AdvancedMainGroup
import com.fivematch.fivemaster.game.utils.gdxGame

class AMainResult(override val screen: ResultScreen): AdvancedMainGroup() {

    private val imgText  = Image(gdxGame.assetsAll.star)
    private val listImg  = AButton(screen, AButton.Type.Next)

    override fun addActorsOnGroup() {
        color.a = 0f

        gdxGame.soundUtil.apply { play(win) }

        addImgMenu()
        addImages()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgText)
        imgText.setBounds(203f, 960f, 675f, 301f)
    }

    private fun addImages() {
        addActor(listImg)
        listImg.setBounds(301f, 636f, 491f, 173f)
        listImg.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
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