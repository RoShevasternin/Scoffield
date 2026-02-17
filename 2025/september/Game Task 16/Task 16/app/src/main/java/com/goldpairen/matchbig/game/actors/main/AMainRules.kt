package com.goldpairen.matchbig.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.goldpairen.matchbig.game.actors.button.AButton
import com.goldpairen.matchbig.game.screens.RulesScreen
import com.goldpairen.matchbig.game.utils.*
import com.goldpairen.matchbig.game.utils.actor.animDelay
import com.goldpairen.matchbig.game.utils.actor.animHide
import com.goldpairen.matchbig.game.utils.actor.animShow
import com.goldpairen.matchbig.game.utils.actor.setBounds
import com.goldpairen.matchbig.game.utils.actor.setOnClickListener
import com.goldpairen.matchbig.game.utils.advanced.AdvancedMainGroup

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.RULES)
    private val btnMenu = Actor() //AButton(screen, AButton.Type.X)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(115f, 416f, 850f, 1087f)
    }

    private fun addBtnS() {
        addActor(btnMenu)
        btnMenu.setBounds(833f, 416f, 132f, 132f)

        btnMenu.setOnClickListener(gdxGame.soundUtil) {
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