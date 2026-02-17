package com.plinkray.ioosudmarine.game.manager.util

import com.plinkray.ioosudmarine.game.manager.SpriteManager
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

          val ball  = getRegionAll("ball")
          val bc_d  = getRegionAll("bc_d")
          val bc_p  = getRegionAll("bc_p")
          val click = getRegionAll("click")
          val els   = getRegionAll("els")
          val ex_d  = getRegionAll("ex_d")
          val ex_p  = getRegionAll("ex_p")
          val l1_d  = getRegionAll("l1_d")
          val l1_p  = getRegionAll("l1_p")
          val l2_d  = getRegionAll("l2_d")
          val l2_p  = getRegionAll("l2_p")
          val l3_d  = getRegionAll("l3_d")
          val l3_p  = getRegionAll("l3_p")
          val rul_d = getRegionAll("rul_d")
          val rul_p = getRegionAll("rul_p")

          // textures ------------------------------------------------------------------------------
          val _1  = SpriteManager.EnumTexture._1.data.texture
          val _2  = SpriteManager.EnumTexture._2.data.texture
          val _3  = SpriteManager.EnumTexture._3.data.texture
          val RULES   = SpriteManager.EnumTexture.RULES.data.texture

     }

}