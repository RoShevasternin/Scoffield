package com.skypro.doger.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.skypro.doger.game.actors.label.ALabelStyle
import com.skypro.doger.game.manager.SpriteManager
import com.skypro.doger.game.utils.actor.setBounds
import com.skypro.doger.game.utils.advanced.AdvancedGroup
import com.skypro.doger.game.utils.Layout.Panel as LP

class Panel: AdvancedGroup() {

    private val panel = Image(SpriteManager.GameRegion.PANEL.region)
    val text          = Label("0", ALabelStyle.style(ALabelStyle.Inter.Light._57))


    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }


    private fun addActorsOnGroup() {
        addAndFillActor(panel)
        addText()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun addText() {
        addActor(text)
        text.setBounds(LP.text)
        text.setAlignment(Align.center)
    }

}