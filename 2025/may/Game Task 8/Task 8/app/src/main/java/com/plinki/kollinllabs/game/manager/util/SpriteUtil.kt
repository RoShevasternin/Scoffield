package com.plinki.kollinllabs.game.manager.util

import com.plinki.kollinllabs.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("logo")

          val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

     class All {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          // atlas All ------------------------------------------------------------------------------

          val b_d        = getRegionAll("b_d")
          val b_p        = getRegionAll("b_p")
          val blob       = getRegionAll("blob")
          val blu        = getRegionAll("blu")
          val click      = getRegionAll("click")
          val e_d        = getRegionAll("e_d")
          val e_p        = getRegionAll("e_p")
          val p_d        = getRegionAll("p_d")
          val p_p        = getRegionAll("p_p")
          val r_d        = getRegionAll("r_d")
          val r_p        = getRegionAll("r_p")
          val red        = getRegionAll("red")

          // textures ------------------------------------------------------------------------------
          val GAME  = SpriteManager.EnumTexture.GAME.data.texture
          val RULES = SpriteManager.EnumTexture.RULES.data.texture

     }

}