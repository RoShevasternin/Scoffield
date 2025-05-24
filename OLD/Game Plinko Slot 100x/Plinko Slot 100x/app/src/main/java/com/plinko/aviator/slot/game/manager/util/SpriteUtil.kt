package com.plinko.aviator.slot.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.plinko.aviator.slot.game.manager.SpriteManager

class SpriteUtil {

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val ball  = getRegion("ball")
          val click = getRegion("click")

          val PLINKO_MAIN = SpriteManager.EnumTexture.PLINKO_MAIN.data.texture
     }

}