package com.filermax.detoxer.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.filermax.detoxer.game.actors.label.ALabelStyle
import com.filermax.detoxer.game.manager.SpriteManager
import com.filermax.detoxer.game.utils.GameColor
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.actor.toClickable
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import com.filermax.detoxer.game.actors.label.ALabelStyle.Mulish.Medium as SMMedium
import com.filermax.detoxer.game.utils.Layout.Back as LB

class Back(
    private val titleText: String,
): AdvancedGroup() {

    private val titleLabel = Label(titleText, ALabelStyle.style(SMMedium._30, GameColor.blue))
    private val image      = Image(SpriteManager.CommonRegion.BACK.region)



    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }


    private fun addActorsOnGroup() {
        addTitle()
        addImage()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addTitle() {
        addActor(titleLabel)
        titleLabel.setBounds(LB.title)
    }

    private fun addImage() {
        addActor(image)
        image.setBounds(LB.image)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    fun setOnClickListener(block: () -> Unit = {}) {
        toClickable().setOnClickListener { block() }
    }

}