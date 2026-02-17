package com.bonanza.sweetcards.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.bonanza.sweetcards.game.manager.SpriteManager

data class AButtonStyle(
    val default : TextureRegion,
    val pressed : TextureRegion,
    val disabled: TextureRegion? = null,
) {
    
//    companion object {
//        val right get() = AButtonStyle(
//            default = SpriteManager.GameRegion.RIGHT_DEF.region,
//            pressed = SpriteManager.GameRegion.RIGHT_PRESS.region,
//        )
//        val left get() = AButtonStyle(
//            default = SpriteManager.GameRegion.LEFT_DEF.region,
//            pressed = SpriteManager.GameRegion.LEFT_PRESS.region,
//        )
//        val info get() = AButtonStyle(
//            default = SpriteManager.GameRegion.INFO_DEF.region,
//            pressed = SpriteManager.GameRegion.INFO_PRESS.region,
//        )
//    }
    
}