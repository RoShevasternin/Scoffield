package com.plinkray.ioosudmarine.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinkray.ioosudmarine.game.actors.button.AButton
import com.plinkray.ioosudmarine.game.screens.RulesScreen
import com.plinkray.ioosudmarine.game.utils.Block
import com.plinkray.ioosudmarine.game.utils.TIME_ANIM_SCREEN
import com.plinkray.ioosudmarine.game.utils.actor.animDelay
import com.plinkray.ioosudmarine.game.utils.actor.animHide
import com.plinkray.ioosudmarine.game.utils.actor.animShow
import com.plinkray.ioosudmarine.game.utils.actor.setBounds
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedMainGroup
import com.plinkray.ioosudmarine.game.utils.gdxGame

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
        imgRules.setBounds(51f, 382f, 978f, 1373f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(190f, 157f, 700f, 150f)
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