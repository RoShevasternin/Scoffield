package com.appwin.fight.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.appwin.fight.game.actors.button.AButton
import com.appwin.fight.game.screens.RulesScreen
import com.appwin.fight.game.utils.*
import com.appwin.fight.game.utils.actor.animDelay
import com.appwin.fight.game.utils.actor.animHide
import com.appwin.fight.game.utils.actor.animShow
import com.appwin.fight.game.utils.advanced.AdvancedMainGroup

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.RULE)
    private val btnX      = AButton(screen, AButton.Type.X)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(146f, 734f, 787f, 720f)
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