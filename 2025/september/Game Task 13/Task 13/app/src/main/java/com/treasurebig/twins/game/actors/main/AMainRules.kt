package com.treasurebig.twins.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.treasurebig.twins.game.actors.button.AButton
import com.treasurebig.twins.game.screens.RulesScreen
import com.treasurebig.twins.game.utils.*
import com.treasurebig.twins.game.utils.actor.animDelay
import com.treasurebig.twins.game.utils.actor.animHide
import com.treasurebig.twins.game.utils.actor.animShow
import com.treasurebig.twins.game.utils.actor.setBounds
import com.treasurebig.twins.game.utils.advanced.AdvancedMainGroup

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules  = Image(gdxGame.assetsAll.RELUS)
    private val btnMenu = AButton(screen, AButton.Type.X)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRls()
        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRls() {
        addActor(imgRules)
        imgRules.setBounds(268f, 250f, 1463f, 701f)
    }

    private fun addBtnS() {
        addActor(btnMenu)
        btnMenu.setBounds(1837f, 1028f, 122f, 122f)

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