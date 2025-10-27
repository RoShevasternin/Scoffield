package com.plinko.ballwinx100.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen

class TopIconButton(
    override val screen: AdvancedScreen,
    private val icon: TextureRegion,
    private val backgroundAsset: TextureRegion,
    ) : AdvancedGroup() {

    override fun addActorsOnGroup() {
        addBackground()
        addIconImage()
    }

    private fun addIconImage() {
        val iconImage = Image(icon)
        val iconWidth = icon.regionWidth.toFloat()
        val iconHeight = icon.regionHeight.toFloat()
        iconImage.setBounds(
            this.width/2 - iconWidth/2,
            this.height/2 - iconHeight/2,
            iconWidth,
            iconHeight
        )
        addActor(iconImage)
    }

    private fun addBackground() {
        val backgroundImage = Image(backgroundAsset)
        backgroundImage.width = this.width
        backgroundImage.height = this.height
        addAndFillActor(backgroundImage)
    }

    override fun getHeight(): Float {
        return backgroundAsset.regionHeight.toFloat()
    }

    override fun getWidth(): Float {
        return backgroundAsset.regionWidth.toFloat()
    }


}