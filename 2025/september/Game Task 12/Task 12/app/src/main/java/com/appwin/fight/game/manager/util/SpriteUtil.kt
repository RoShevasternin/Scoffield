package com.appwin.fight.game.manager.util

import com.appwin.fight.game.manager.SpriteManager
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

          val menu       = getRegionAll("menu")
          val next_def   = getRegionAll("next_def")
          val next_press = getRegionAll("next_press")
          val off        = getRegionAll("off")
          val on         = getRegionAll("on")
          val sett       = getRegionAll("sett")
          val x_def      = getRegionAll("x_def")
          val x_press    = getRegionAll("x_press")

          // textures ------------------------------------------------------------------------------

          val RULE  = SpriteManager.EnumTexture.RULE.data.texture
          val SONCE = SpriteManager.EnumTexture.SONCE.data.texture
          val TEXT  = SpriteManager.EnumTexture.TEXT.data.texture

         val BACK1 = SpriteManager.EnumTexture.BACK1.data.texture
         val BACK2 = SpriteManager.EnumTexture.BACK2.data.texture
         val BACK3 = SpriteManager.EnumTexture.BACK3.data.texture

          private val _1 = SpriteManager.EnumTexture._1.data.texture
          private val _2 = SpriteManager.EnumTexture._2.data.texture
          private val _3 = SpriteManager.EnumTexture._3.data.texture
          private val _4 = SpriteManager.EnumTexture._4.data.texture
          private val _5 = SpriteManager.EnumTexture._5.data.texture
          private val _6 = SpriteManager.EnumTexture._6.data.texture

          val listPuzle = listOf(_1, _2, _3, _4, _5, _6)

     }

}