package com.sandtro.trintity.game.manager.util

import com.sandtro.trintity.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("loa")

          //val BACKGROUND = SpriteManager.EnumTexture.L_BACKGROUND.data.texture

     }

    class All {
        private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
        //private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

        // atlas All ------------------------------------------------------------------------------

        val locters = getRegionAll("locters")
        val mu      = getRegionAll("mu")
        val off     = getRegionAll("off")
        val on      = getRegionAll("on")
        val prd     = getRegionAll("prd")
        val prp     = getRegionAll("prp")
        val ttt     = getRegionAll("ttt")
        val win     = getRegionAll("win")
        val xd      = getRegionAll("xd")
        val xp      = getRegionAll("xp")

        val listItem = List(5) { getRegionAll("${it.inc()}") }

        // textures ------------------------------------------------------------------------------

        val CIRCULCERA = SpriteManager.EnumTexture.CIRCULCERA.data.texture
        val GAMM       = SpriteManager.EnumTexture.GAMM.data.texture
        val DRAK       = SpriteManager.EnumTexture.DRAK.data.texture
        val LOSS       = SpriteManager.EnumTexture.LOSS.data.texture
        val RRR        = SpriteManager.EnumTexture.RRR.data.texture
        val SSS        = SpriteManager.EnumTexture.SSS.data.texture
        val WINN       = SpriteManager.EnumTexture.WINN.data.texture
     }

}