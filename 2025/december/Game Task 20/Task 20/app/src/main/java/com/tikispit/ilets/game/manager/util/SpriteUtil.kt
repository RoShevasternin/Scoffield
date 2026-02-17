package com.tikispit.ilets.game.manager.util

import com.tikispit.ilets.game.manager.SpriteManager
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

        val bd    = getRegionAll("bd")
        val bp    = getRegionAll("bp")
        val md    = getRegionAll("md")
        val mp    = getRegionAll("mp")
        val palun = getRegionAll("palun")
        val sd    = getRegionAll("sd")
        val sp    = getRegionAll("sp")

        //val listItem = List(12) { getRegionAll("${it.inc()}") }

        // textures ------------------------------------------------------------------------------

        val _1        = SpriteManager.EnumTexture._1.data.texture
        val _2        = SpriteManager.EnumTexture._2.data.texture
        val _3        = SpriteManager.EnumTexture._3.data.texture
        val _4        = SpriteManager.EnumTexture._4.data.texture
        val _5        = SpriteManager.EnumTexture._5.data.texture
        val _6        = SpriteManager.EnumTexture._6.data.texture
        val _7        = SpriteManager.EnumTexture._7.data.texture
        val BACKGREG  = SpriteManager.EnumTexture.BACKGREG.data.texture
        val GAMR      = SpriteManager.EnumTexture.GAMR.data.texture
        val GREN      = SpriteManager.EnumTexture.GREN.data.texture
        val REGD      = SpriteManager.EnumTexture.REGD.data.texture
        val RUSEL     = SpriteManager.EnumTexture.RUSEL.data.texture
        val SER       = SpriteManager.EnumTexture.SER.data.texture
        val VIC       = SpriteManager.EnumTexture.VIC.data.texture

        val listPuzzles = listOf(_1, _2, _3, _4, _5, _6, _7,)
     }

}