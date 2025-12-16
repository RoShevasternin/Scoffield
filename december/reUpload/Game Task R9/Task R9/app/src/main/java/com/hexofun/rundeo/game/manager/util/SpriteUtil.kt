package com.hexofun.rundeo.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.hexofun.rundeo.game.manager.SpriteManager

class SpriteUtil {

     class StartAssets {
          val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
          val lop = SpriteManager.EnumTexture.lop.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val hexagon     = getRegion("hexagon")
          val menu_def    = getRegion("menu_def")
          val menu_press  = getRegion("menu_press")
          val panel       = getRegion("panel")
          val pause_check = getRegion("pause_check")
          val pause_def   = getRegion("pause_def")
          val rotate      = getRegion("rotate")
          val star        = getRegion("star")
          val triangle    = getRegion("triangle")
          val up          = getRegion("up")
          val volume_item = getRegion("volume_item")

          val colors = List(6) { getRegion("c${it.inc()}") }

          val _2       = SpriteManager.EnumTexture._2.data.texture
          val _3       = SpriteManager.EnumTexture._3.data.texture
          val _4       = SpriteManager.EnumTexture._4.data.texture
          val map      = SpriteManager.EnumTexture.map.data.texture
          val menu     = SpriteManager.EnumTexture.menu.data.texture
          val rules    = SpriteManager.EnumTexture.rules.data.texture
          val settings = SpriteManager.EnumTexture.settings.data.texture
     }

}