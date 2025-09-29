package com.treasurebig.twins.game.manager.util

import com.treasurebig.twins.game.manager.SpriteManager
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

         val menu      = getRegionAll("menu")
         val muz_def   = getRegionAll("muz_def")
         val muz_dis   = getRegionAll("muz_dis")
         val sound_def = getRegionAll("sound_def")
         val sound_dis = getRegionAll("sound_dis")
         val sundec    = getRegionAll("sundec")
         val x_def     = getRegionAll("x_def")
         val x_prs     = getRegionAll("x_prs")

          // textures ------------------------------------------------------------------------------

         val BACK1 = SpriteManager.EnumTexture.BACK1.data.texture

         val RELUS   = SpriteManager.EnumTexture.RELUS.data.texture
         val SETTINS = SpriteManager.EnumTexture.SETTINS.data.texture

         private val _1 = SpriteManager.EnumTexture._1.data.texture
         private val _2 = SpriteManager.EnumTexture._2.data.texture
         private val _3 = SpriteManager.EnumTexture._3.data.texture
         private val _4 = SpriteManager.EnumTexture._4.data.texture
         private val _5 = SpriteManager.EnumTexture._5.data.texture
         private val _6 = SpriteManager.EnumTexture._6.data.texture
         private val _7 = SpriteManager.EnumTexture._7.data.texture
         private val _8 = SpriteManager.EnumTexture._8.data.texture
         private val _9 = SpriteManager.EnumTexture._9.data.texture
         private val _10 = SpriteManager.EnumTexture._10.data.texture

         val listItems = listOf(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10)

     }

}