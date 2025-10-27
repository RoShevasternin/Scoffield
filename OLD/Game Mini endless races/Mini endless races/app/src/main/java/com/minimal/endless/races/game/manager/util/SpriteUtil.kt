package com.minimal.endless.races.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.minimal.endless.races.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          val mini = SpriteManager.EnumTexture.mini.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val car         = getRegion("car")
          val left_def    = getRegion("left_def")
          val left_press  = getRegion("left_press")
          val right_def   = getRegion("right_def")
          val right_press = getRegion("right_press")

          val leftCars  = List(5) { getRegion("l${it.inc()}") }
          val rightCars = List(5) { getRegion("r${it.inc()}") }
     }

}