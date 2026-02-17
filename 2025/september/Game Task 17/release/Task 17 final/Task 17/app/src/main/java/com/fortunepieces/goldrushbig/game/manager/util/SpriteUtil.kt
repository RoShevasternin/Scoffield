package com.fortunepieces.goldrushbig.game.manager.util

import com.fortunepieces.goldrushbig.game.manager.SpriteManager
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

        val back_def  = getRegionAll("back_def")
        val back_prs  = getRegionAll("back_prs")
        val btns      = getRegionAll("btns")
        val left_def  = getRegionAll("left_def")
        val left_prs  = getRegionAll("left_prs")
        val play      = getRegionAll("play")
        val pouse     = getRegionAll("pouse")
        val right_def = getRegionAll("right_def")
        val right_prs = getRegionAll("right_prs")
        val timer     = getRegionAll("timer")
        val mus_def   = getRegionAll("mus_def")
        val mus_prs   = getRegionAll("mus_prs")
        val pl_def    = getRegionAll("pl_def")
        val pl_prs    = getRegionAll("pl_prs")
        val sound_def = getRegionAll("sound_def")
        val sound_prs = getRegionAll("sound_prs")

        // textures ------------------------------------------------------------------------------

        val BACKGRANDE = SpriteManager.EnumTexture.BACKGRANDE.data.texture
        val CAP        = SpriteManager.EnumTexture.CAP.data.texture
        val HUARAP     = SpriteManager.EnumTexture.HUARAP.data.texture
        val LOSE       = SpriteManager.EnumTexture.LOSE.data.texture
        val LOSSSEP    = SpriteManager.EnumTexture.LOSSSEP.data.texture
        val WIN        = SpriteManager.EnumTexture.WIN.data.texture
        val WINER      = SpriteManager.EnumTexture.WINER.data.texture

        private val _1 = SpriteManager.EnumTexture._1.data.texture
        private val _2 = SpriteManager.EnumTexture._2.data.texture
        private val _3 = SpriteManager.EnumTexture._3.data.texture
        private val _4 = SpriteManager.EnumTexture._4.data.texture
        private val _5 = SpriteManager.EnumTexture._5.data.texture
        private val _6 = SpriteManager.EnumTexture._6.data.texture

        val listPuzle = listOf(_1, _2, _3, _4, _5, _6)

     }

}