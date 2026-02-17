package com.triplees.linees.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.triplees.linees.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          val piramida = SpriteManager.EnumTexture.piramida.data.texture
          val loa = SpriteManager.EnumTexture.loa.data.texture
     }

     class Assets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ASSETS.data.atlas.findRegion(name)

          val list = List(6) { getRegion("${it.inc()}") }

          val menu_def    = getRegion("menu_def")
          val menu_press  = getRegion("menu_press")
          val pause_def   = getRegion("pause_def")
          val pause_press = getRegion("pause_press")
          val time_panel  = getRegion("time_panel")
          val isoca       = getRegion("isoca")

          val Fail       = SpriteManager.EnumTexture.Fail.data.texture
          val horizontal = SpriteManager.EnumTexture.horizontal.data.texture
          val menu       = SpriteManager.EnumTexture.menu.data.texture
          val rules      = SpriteManager.EnumTexture.rules.data.texture
          val settings   = SpriteManager.EnumTexture.settings.data.texture
          val vertical   = SpriteManager.EnumTexture.vertical.data.texture
          val Win        = SpriteManager.EnumTexture.Win.data.texture
          val you_lose   = SpriteManager.EnumTexture.you_lose.data.texture
          val you_win    = SpriteManager.EnumTexture.you_win.data.texture
     }

}