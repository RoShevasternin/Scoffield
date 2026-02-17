package com.nicelute.fireworks.game.manager.util

import com.nicelute.fireworks.game.manager.SpriteManager

class SpriteUtil {

     class AllAssets {
          val btn = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion("btn")

          val Menu  = SpriteManager.EnumTexture.Menu.data.texture
          val firre = SpriteManager.EnumTexture.firre.data.texture
          val f2 = SpriteManager.EnumTexture.f2.data.texture
          val f3 = SpriteManager.EnumTexture.f3.data.texture
          val f4 = SpriteManager.EnumTexture.f4.data.texture
          val f5 = SpriteManager.EnumTexture.f5.data.texture
          val f6 = SpriteManager.EnumTexture.f6.data.texture

          val backs = listOf(
               firre,
                       f2,
                       f3,
                       f4,
                       f5,
                       f6,
          )

     }

}