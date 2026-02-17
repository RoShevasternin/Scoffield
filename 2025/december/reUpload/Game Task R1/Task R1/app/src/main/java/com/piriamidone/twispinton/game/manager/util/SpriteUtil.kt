package com.piriamidone.twispinton.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.piriamidone.twispinton.game.manager.SpriteManager

class SpriteUtil {

     class LoadAssets {
          val background    = SpriteManager.EnumTexture.background.data.texture
          val loader_mask   = SpriteManager.EnumTexture.loader_mask.data.texture
          val loading       = SpriteManager.EnumTexture.loading.data.texture
          val phara         = SpriteManager.EnumTexture.phara.data.texture
          val progress      = SpriteManager.EnumTexture.progress.data.texture
          val progress_back = SpriteManager.EnumTexture.progress_back.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.All.data.atlas.findRegion(name)

          val m_def          = getRegion("m_def")
          val m_press        = getRegion("m_press")
          val off            = getRegion("off")
          val on             = getRegion("on")
          val time           = getRegion("time")
          val timer_progress = getRegion("timer_progress")

          val levels = List(4) { getRegion("${it.inc()}") }

          val fail        = SpriteManager.EnumTexture.fail.data.texture
          val gamer       = SpriteManager.EnumTexture.gamer.data.texture
          val level       = SpriteManager.EnumTexture.level.data.texture
          val menu        = SpriteManager.EnumTexture.menu.data.texture
          val phara_panel = SpriteManager.EnumTexture.phara_panel.data.texture
          val puzzle_mask = SpriteManager.EnumTexture.puzzle_mask.data.texture
          val rules       = SpriteManager.EnumTexture.rules.data.texture
          val sett        = SpriteManager.EnumTexture.sett.data.texture
          val timer_mask  = SpriteManager.EnumTexture.timer_mask.data.texture
          val win         = SpriteManager.EnumTexture.win.data.texture

          val _1         = SpriteManager.EnumTexture._1.data.texture
          val _2         = SpriteManager.EnumTexture._2.data.texture
          val _3         = SpriteManager.EnumTexture._3.data.texture
          val _4         = SpriteManager.EnumTexture._4.data.texture

          val puzzleList = listOf(_1,_2,_3,_4)
     }

}