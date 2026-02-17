package com.bonanza.twoursenderson.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bonanza.twoursenderson.game.actors.button.AButton
import com.bonanza.twoursenderson.game.screens.RulesScreen
import com.bonanza.twoursenderson.game.utils.*
import com.bonanza.twoursenderson.game.utils.actor.animDelay
import com.bonanza.twoursenderson.game.utils.actor.animHide
import com.bonanza.twoursenderson.game.utils.actor.animShow
import com.bonanza.twoursenderson.game.utils.actor.setBounds
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedMainGroup

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.RLES)
    private val btnMenu = AButton(screen, AButton.Type.Back)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(39f, 312f, 1921f, 802f)
    }

    private fun addBtnS() {
        addActor(btnMenu)
        btnMenu.setBounds(777f, 58f, 446f, 217f)

        btnMenu.setOnClickListener {
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