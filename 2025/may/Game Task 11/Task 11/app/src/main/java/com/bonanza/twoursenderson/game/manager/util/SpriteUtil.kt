package com.bonanza.twoursenderson.game.manager.util

import com.bonanza.twoursenderson.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("lodr")

          val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          //private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // atlas All ------------------------------------------------------------------------------

          val listItems = List(10) { getRegionAll("${it.inc()}") }

          val bk_def   = getRegionAll("bk_def")
          val bk_press = getRegionAll("bk_press")
          val ex_def   = getRegionAll("ex_def")
          val ex_press = getRegionAll("ex_press")
          val pl_def   = getRegionAll("pl_def")
          val pl_press = getRegionAll("pl_press")
          val rl_def   = getRegionAll("rl_def")
          val rl_press = getRegionAll("rl_press")
          val wtf      = getRegionAll("wtf")

          // textures ------------------------------------------------------------------------------

          val BACKGR = SpriteManager.EnumTexture.BACKGR.data.texture
          val RLES   = SpriteManager.EnumTexture.RLES.data.texture

     }

}