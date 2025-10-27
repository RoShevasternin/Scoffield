package com.fork2d.lift2d.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.fork2d.lift2d.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          val GARAGES = SpriteManager.EnumTexture.GARAGES.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val box           = getRegion("box")
          val big_wheel     = getRegion("big_wheel")
          val control_panel = getRegion("control_panel")
          val fork          = getRegion("fork")
          val mini_wheel    = getRegion("mini_wheel")
          val tractor       = getRegion("tractor")
     }

}