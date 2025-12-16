package com.watermil.terwater.game.manager.util

import com.watermil.terwater.game.manager.SpriteManager
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

        val golde   = getRegionAll("golde")
        val md      = getRegionAll("md")
        val menu    = getRegionAll("menu")
        val mp      = getRegionAll("mp")
        val project = getRegionAll("project")
        val sd      = getRegionAll("sd")
        val sp      = getRegionAll("sp")
        val xd      = getRegionAll("xd")
        val xp      = getRegionAll("xp")
        val wenus   = getRegionAll("wenus")

        val listItem = List(9) { getRegionAll("${it.inc()}") }

        // textures ------------------------------------------------------------------------------

        val mda        = SpriteManager.EnumTexture.mda.data.texture
        val ridingtone = SpriteManager.EnumTexture.ridingtone.data.texture
        val rules      = SpriteManager.EnumTexture.rules.data.texture
        val table      = SpriteManager.EnumTexture.table.data.texture
        val water      = SpriteManager.EnumTexture.water.data.texture
        val yha        = SpriteManager.EnumTexture.yha.data.texture
     }

}