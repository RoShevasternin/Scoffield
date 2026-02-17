package com.obezana.playtocrypto.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.obezana.playtocrypto.game.manager.SpriteManager

data class AButtonStyle(
    val default : TextureRegion,
    val pressed : TextureRegion,
    val disabled: TextureRegion? = null,
) {
    
    companion object {
//        val restik get() = AButtonStyle(
//            default = SpriteManager.GameRegion.pausa.region,
//            pressed = SpriteManager.GameRegion.pla.region,
//            disabled = SpriteManager.GameRegion.pla.region,
//        )
    }
    
}