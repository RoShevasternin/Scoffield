package com.plinko.ballwinx100.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen

class MenuButton(
    private val textAsset: TextureRegion,
    override val screen: AdvancedScreen,
    ) : AdvancedGroup() {

    private val backgroundAsset = screen.game.mainAssets.BACK_BUTTON_1
    private val scaleRatio = 1.1f

    override fun addActorsOnGroup() {
        addBackground()
        addLabelImage()
    }

    private fun addLabelImage() {
        val textImage = Image(textAsset)
        textImage.setBounds(
            this.width/2 - (textImage.width * scaleRatio)/2,
            this.height*0.5f - (textImage.height * scaleRatio)/2,
            textImage.width * scaleRatio,
            textImage.height * scaleRatio
        )
        addActor(textImage)
    }

    private fun addBackground() {
        val backgroundImage = Image(backgroundAsset)
        backgroundImage.width = this.width
        backgroundImage.height = this.height
        addAndFillActor(backgroundImage)
    }

    override fun getHeight(): Float {
        return backgroundAsset.regionHeight.toFloat() * scaleRatio
    }

    override fun getWidth(): Float {
        return backgroundAsset.regionWidth.toFloat() * scaleRatio
    }


}