package com.fivematch.fivemaster.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fivematch.fivemaster.game.actors.button.AButton
import com.fivematch.fivemaster.game.screens.RulesScreen
import com.fivematch.fivemaster.game.utils.Block
import com.fivematch.fivemaster.game.utils.TIME_ANIM_SCREEN
import com.fivematch.fivemaster.game.utils.actor.animDelay
import com.fivematch.fivemaster.game.utils.actor.animHide
import com.fivematch.fivemaster.game.utils.actor.animShow
import com.fivematch.fivemaster.game.utils.actor.setOnClickListener
import com.fivematch.fivemaster.game.utils.advanced.AdvancedMainGroup
import com.fivematch.fivemaster.game.utils.gdxGame

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgText  = Image(gdxGame.assetsAll.tesik)
    private val listImg  = AButton(screen, AButton.Type.Back)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addImages()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgText)
        imgText.setBounds(45f, 319f, 989f, 1455f)
    }

    private fun addImages() {
        addActor(listImg)
        listImg.setBounds(294f, 36f, 491f, 173f)
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