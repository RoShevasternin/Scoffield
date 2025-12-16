package com.dliw.drib.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dliw.drib.game.manager.SpriteManager

class SpriteUtil {

     class StartAssets {
          val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
          val LOAD       = SpriteManager.EnumTexture.LOAD.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val counter     = getRegion("counter")
          val music_def   = getRegion("music_def")
          val music_press = getRegion("music_press")
          val panel       = getRegion("panel")
          val pause_def   = getRegion("pause_def")
          val pause_press = getRegion("pause_press")
          val sound_def   = getRegion("sound_def")
          val sound_press = getRegion("sound_press")
          val menu        = getRegion("menu")
          val beam        = getRegion("beam")

          val birds  = List(3) { getRegion("${it.inc()}") }
          val a_bird = List(2) { getRegion("a${it.inc()}") }
          val b_bird = List(2) { getRegion("b${it.inc()}") }
          val c_bird = List(2) { getRegion("c${it.inc()}") }
     }

}