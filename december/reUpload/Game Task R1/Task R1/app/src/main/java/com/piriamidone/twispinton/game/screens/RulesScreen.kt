package com.piriamidone.twispinton.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.piriamidone.twispinton.game.LibGDXGame
import com.piriamidone.twispinton.game.utils.actor.setOnClickListener
import com.piriamidone.twispinton.game.utils.advanced.AdvancedScreen
import com.piriamidone.twispinton.game.utils.advanced.AdvancedStage
import com.piriamidone.twispinton.game.utils.region

class RulesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val panelImg = Image(game.alllAssets.phara_panel)
    private val rulesImg  = Image(game.alllAssets.rules)


    override fun show() {
        setBackgrounds(game.loadAssets.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelImg()
        addRules()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.setBounds(0f, 0f, 966f, 1603f)

        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(730f, 341f, 171f, 175f)
            setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
        }
    }

    private fun AdvancedStage.addRules() {
        addActor(rulesImg)
        rulesImg.setBounds(255f, 858f, 619f, 894f)
    }

}