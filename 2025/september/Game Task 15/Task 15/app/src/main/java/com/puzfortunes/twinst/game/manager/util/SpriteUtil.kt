package com.puzfortunes.twinst.game.manager.util

import com.puzfortunes.twinst.game.manager.SpriteManager
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

        val frame   = getRegionAll("frame")
        val mus_off = getRegionAll("mus_off")
        val mus_on  = getRegionAll("mus_on")
        val sou_off = getRegionAll("sou_off")
        val sou_on  = getRegionAll("sou_on")

        // textures ------------------------------------------------------------------------------

        val BACK     = SpriteManager.EnumTexture.BACK.data.texture
        val BACK_WIN = SpriteManager.EnumTexture.BACK_WIN.data.texture
        val MENU     = SpriteManager.EnumTexture.MENU.data.texture
        val RULES    = SpriteManager.EnumTexture.RULES.data.texture
        val WINES    = SpriteManager.EnumTexture.WINES.data.texture

        private val _1 = SpriteManager.EnumTexture._1.data.texture
        private val _2 = SpriteManager.EnumTexture._2.data.texture
        private val _3 = SpriteManager.EnumTexture._3.data.texture
        private val _4 = SpriteManager.EnumTexture._4.data.texture
        private val _5 = SpriteManager.EnumTexture._5.data.texture

        val listPuzle = listOf(_1, _2, _3, _4, _5, )

     }

}