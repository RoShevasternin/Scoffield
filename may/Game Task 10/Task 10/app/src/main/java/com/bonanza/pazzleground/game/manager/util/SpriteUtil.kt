package com.bonanza.pazzleground.game.manager.util

import com.bonanza.pazzleground.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("loader")

          val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val exd        = getRegionAll("exd")
          val exp        = getRegionAll("exp")
          val mcheck     = getRegionAll("mcheck")
          val mdef       = getRegionAll("mdef")
          val mendef     = getRegionAll("mendef")
          val menprs     = getRegionAll("menprs")
          val pausecheck = getRegionAll("pausecheck")
          val pausedef   = getRegionAll("pausedef")
          val pegr       = getRegionAll("pegr")
          val pladef     = getRegionAll("pladef")
          val plpress    = getRegionAll("plpress")
          val progs      = getRegionAll("progs")
          val rd         = getRegionAll("rd")
          val rp         = getRegionAll("rp")
          val tim        = getRegionAll("tim")

          // textures ------------------------------------------------------------------------------

          val MASEK  = SpriteManager.EnumTexture.MASEK.data.texture
          val RILS   = SpriteManager.EnumTexture.RILS.data.texture
          val ROZOVI = SpriteManager.EnumTexture.ROZOVI.data.texture

          private val _1 = SpriteManager.EnumTexture._1.data.texture
          private val _2 = SpriteManager.EnumTexture._2.data.texture
          private val _3 = SpriteManager.EnumTexture._3.data.texture
          private val _4 = SpriteManager.EnumTexture._4.data.texture
          private val _5 = SpriteManager.EnumTexture._5.data.texture
          private val _6 = SpriteManager.EnumTexture._6.data.texture

          val listPuziks = listOf(_1, _2, _3, _4, _5, _6)

     }

}