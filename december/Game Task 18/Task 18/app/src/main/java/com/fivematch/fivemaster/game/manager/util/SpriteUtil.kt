package com.fivematch.fivemaster.game.manager.util

import com.fivematch.fivemaster.game.manager.SpriteManager
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

        val bede  = getRegionAll("bede")
        val bepe  = getRegionAll("bepe")
        val moff  = getRegionAll("moff")
        val mon   = getRegionAll("mon")
        val nd    = getRegionAll("nd")
        val np    = getRegionAll("np")
        val star  = getRegionAll("star")
        val rty   = getRegionAll("rty")
        val ytr   = getRegionAll("ytr")
        val listToy = List(5) { getRegionAll("toy_${it.inc()}") }

        // textures ------------------------------------------------------------------------------

        val musik = SpriteManager.EnumTexture.musik.data.texture
        val pusik = SpriteManager.EnumTexture.pusik.data.texture
        val tesik = SpriteManager.EnumTexture.tesik.data.texture
     }

}