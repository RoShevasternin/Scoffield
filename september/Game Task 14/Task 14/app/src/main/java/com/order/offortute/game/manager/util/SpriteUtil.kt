package com.order.offortute.game.manager.util

import com.order.offortute.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("loader")

          //val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

         val listItems = List(6) { getRegionAll("${it.inc()}") }

         val done_def   = getRegionAll("done_def")
         val done_prs   = getRegionAll("done_prs")
         val menu       = getRegionAll("menu")
         val menu_def   = getRegionAll("menu_def")
         val menu_prs   = getRegionAll("menu_prs")
         val off        = getRegionAll("off")
         val on         = getRegionAll("on")
         val sett_def   = getRegionAll("sett_def")
         val sett_prs   = getRegionAll("sett_prs")
         val timer      = getRegionAll("timer")
         val update_def = getRegionAll("update_def")
         val update_prs = getRegionAll("update_prs")

          // textures ------------------------------------------------------------------------------

         val FRAME    = SpriteManager.EnumTexture.FRAME.data.texture
         val LOSE     = SpriteManager.EnumTexture.LOSE.data.texture
         val RULES    = SpriteManager.EnumTexture.RULES.data.texture
         val SETTINGS = SpriteManager.EnumTexture.SETTINGS.data.texture
         val WIN      = SpriteManager.EnumTexture.WIN.data.texture

         val BACKG = SpriteManager.EnumTexture.BACKG.data.texture

     }

}