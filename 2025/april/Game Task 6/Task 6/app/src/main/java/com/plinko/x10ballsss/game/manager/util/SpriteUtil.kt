package com.plinko.x10ballsss.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.plinko.x10ballsss.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          val FARELLO = SpriteManager.EnumTexture.FARELLO.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val farello  = getRegion("farello")
          val wheel    = getRegion("wheel")
          val asteroid = List(8) { getRegion("${it.inc()}") }
     }

}