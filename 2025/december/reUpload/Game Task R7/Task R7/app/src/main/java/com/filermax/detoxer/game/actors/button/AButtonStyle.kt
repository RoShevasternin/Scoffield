package com.filermax.detoxer.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.filermax.detoxer.game.manager.SpriteManager
import com.filermax.detoxer.game.utils.TextureEmpty
import com.filermax.detoxer.game.utils.region

data class AButtonStyle(
    val default : TextureRegion,
    val pressed : TextureRegion,
    val disabled: TextureRegion? = null,
) {
    
    companion object {
        val btn get() = AButtonStyle(
            default = SpriteManager.CommonRegion.BTN.region,
            pressed = TextureEmpty.region,
            disabled = TextureEmpty.region,
        )
    }
    
}