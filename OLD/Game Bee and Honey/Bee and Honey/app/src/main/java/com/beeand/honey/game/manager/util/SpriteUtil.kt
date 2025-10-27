package com.beeand.honey.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.beeand.honey.game.manager.SpriteManager

class SpriteUtil {

     class StartAssets {
          val YELLOW = SpriteManager.EnumTexture.YELLOW.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val exit  = getRegion("exit")
          val honey = getRegion("honey")
          val play  = getRegion("play")
          val med   = getRegion("med")

          val bee = List(8) { getRegion("${it.inc()}") }

          val BLUE = SpriteManager.EnumTexture.BLUE.data.texture
     }

}