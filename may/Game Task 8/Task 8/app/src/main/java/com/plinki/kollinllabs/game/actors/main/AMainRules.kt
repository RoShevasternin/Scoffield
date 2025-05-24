package com.plinki.kollinllabs.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinki.kollinllabs.game.actors.button.AButton
import com.plinki.kollinllabs.game.screens.RulesScreen
import com.plinki.kollinllabs.game.utils.Block
import com.plinki.kollinllabs.game.utils.TIME_ANIM_SCREEN
import com.plinki.kollinllabs.game.utils.actor.animDelay
import com.plinki.kollinllabs.game.utils.actor.animHide
import com.plinki.kollinllabs.game.utils.actor.animShow
import com.plinki.kollinllabs.game.utils.actor.setBounds
import com.plinki.kollinllabs.game.utils.advanced.AdvancedMainGroup
import com.plinki.kollinllabs.game.utils.gdxGame

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules = Image(gdxGame.assetsAll.RULES)
    private val btnBack  = AButton(screen, AButton.Type.Back)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnBack()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(85f, 637f, 909f, 943f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(190f, 265f, 700f, 150f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }

        blockEnd.invoke()
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }

        blockEnd.invoke()
    }

}