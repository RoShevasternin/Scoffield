package com.bonanza.pazzleground.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bonanza.pazzleground.game.actors.button.AButton
import com.bonanza.pazzleground.game.screens.MenushkaScreen
import com.bonanza.pazzleground.game.screens.RulesScreen
import com.bonanza.pazzleground.game.utils.*
import com.bonanza.pazzleground.game.utils.actor.animDelay
import com.bonanza.pazzleground.game.utils.actor.animHide
import com.bonanza.pazzleground.game.utils.actor.animShow
import com.bonanza.pazzleground.game.utils.actor.setBounds
import com.bonanza.pazzleground.game.utils.advanced.AdvancedMainGroup

class AMainRulkes(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.RILS)
    private val imgPOZ  = Image(gdxGame.assetsAll.ROZOVI)
    private val btnMenu = AButton(screen, AButton.Type.Menu)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addImgROZ()
        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgROZ() {
        addActor(imgPOZ)
        imgPOZ.setBounds(506f, 0f, 450f, 545f)
    }

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(0f, 227f, 1081f, 1631f)
    }

    private fun addBtnS() {
        addActor(btnMenu)
        btnMenu.setBounds(43f, 1729f, 156f, 162f)

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