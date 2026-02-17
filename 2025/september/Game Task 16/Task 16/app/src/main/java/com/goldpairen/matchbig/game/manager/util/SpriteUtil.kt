package com.goldpairen.matchbig.game.manager.util

import com.goldpairen.matchbig.game.manager.SpriteManager
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

         val items = List(15) { getRegionAll("${it.inc()}") }

         val fap    = getRegionAll("fap")
         val pit    = getRegionAll("pit")
         val prog   = getRegionAll("prog")
         val ruchka = getRegionAll("ruchka")

          // textures ------------------------------------------------------------------------------

         val BLURED   = SpriteManager.EnumTexture.BLURED.data.texture
         val LOSS     = SpriteManager.EnumTexture.LOSS.data.texture
         val LOSSSSE  = SpriteManager.EnumTexture.LOSSSSE.data.texture
         val MASK     = SpriteManager.EnumTexture.MASK.data.texture
         val MENU     = SpriteManager.EnumTexture.MENU.data.texture
         val ORIGIN   = SpriteManager.EnumTexture.ORIGIN.data.texture
         val RULES    = SpriteManager.EnumTexture.RULES.data.texture
         val SETT     = SpriteManager.EnumTexture.SETT.data.texture
         val VIKKKKKI = SpriteManager.EnumTexture.VIKKKKKI.data.texture
         val WIN      = SpriteManager.EnumTexture.WIN.data.texture

     }

}