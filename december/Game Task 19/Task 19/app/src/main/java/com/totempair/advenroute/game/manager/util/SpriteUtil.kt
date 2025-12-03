package com.totempair.advenroute.game.manager.util

import com.totempair.advenroute.game.manager.SpriteManager
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

        val choose = getRegionAll("choose")
        val find   = getRegionAll("find")
        val mantu  = getRegionAll("mantu")
        val mschk  = getRegionAll("mschk")
        val msd    = getRegionAll("msd")
        val pan    = getRegionAll("pan")
        val rectl  = getRegionAll("rectl")
        val rules  = getRegionAll("rules")
        val sd     = getRegionAll("sd")
        val snchk  = getRegionAll("snchk")
        val snd    = getRegionAll("snd")
        val sp     = getRegionAll("sp")
        val xd     = getRegionAll("xd")
        val xp     = getRegionAll("xp")

        val listItem = List(12) { getRegionAll("${it.inc()}") }

        // textures ------------------------------------------------------------------------------

        val B1    = SpriteManager.EnumTexture.B1.data.texture
        val B2    = SpriteManager.EnumTexture.B2.data.texture
        val B3    = SpriteManager.EnumTexture.B3.data.texture
        val BACK1 = SpriteManager.EnumTexture.BACK1.data.texture
        val BACK2 = SpriteManager.EnumTexture.BACK2.data.texture
        val FAIL  = SpriteManager.EnumTexture.FAIL.data.texture
        val MENU  = SpriteManager.EnumTexture.MENU.data.texture
        val TRY   = SpriteManager.EnumTexture.TRY.data.texture
        val WELL  = SpriteManager.EnumTexture.WELL.data.texture
        val WIN   = SpriteManager.EnumTexture.WIN.data.texture

        val ggg = SpriteManager.EnumTexture.ggg.data.texture
        val ppf = SpriteManager.EnumTexture.ppf.data.texture
        val ppp = SpriteManager.EnumTexture.ppp.data.texture
     }

}