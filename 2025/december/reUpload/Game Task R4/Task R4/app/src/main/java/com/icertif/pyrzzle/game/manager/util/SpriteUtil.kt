package com.icertif.pyrzzle.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.icertif.pyrzzle.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.Loader.data.atlas.findRegion(name)

          val loader = getRegion("loader")

          val eqiptian = SpriteManager.EnumTexture.eqiptian.data.texture
          val maska    = SpriteManager.EnumTexture.maska.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.All.data.atlas.findRegion(name)

          val four_rect   = getRegion("four_rect")
          val menu_def    = getRegion("menu_def")
          val menu_press  = getRegion("menu_press")
          val musicality  = getRegion("musicality")
          val pause_def   = getRegion("pause_def")
          val pause_press = getRegion("pause_press")
          val timer_panel = getRegion("timer_panel")

          val pazles = List(6) { getRegion("${it.inc()}") }
          val levels = List(3) { getRegion("l${it.inc()}") }

          val gamek    = SpriteManager.EnumTexture.gamek.data.texture
          val ggg      = SpriteManager.EnumTexture.ggg.data.texture
          val level    = SpriteManager.EnumTexture.level.data.texture
          val menu     = SpriteManager.EnumTexture.menu.data.texture
          val miskagu  = SpriteManager.EnumTexture.miskagu.data.texture
          val rrr      = SpriteManager.EnumTexture.rrr.data.texture
          val rules    = SpriteManager.EnumTexture.rules.data.texture
          val settings = SpriteManager.EnumTexture.settings.data.texture
          val bubinka  = SpriteManager.EnumTexture.bubinka.data.texture
     }

}