package com.secrofthe.lostpaircret.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.secrofthe.lostpaircret.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.Loading.data.atlas.findRegion(name)

          val bloading = getRegion("bloading")
          val cloading = getRegion("cloading")
          val coboy    = getRegion("coboy")
          val ploading = getRegion("ploading")

          val fulikula = SpriteManager.EnumTexture.fulikula.data.texture
          val matrix   = SpriteManager.EnumTexture.matrix.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.All.data.atlas.findRegion(name)

          val memories = List(10) { getRegion("${it.inc()}") }

          val criket = getRegion("criket")
          val mnu    = getRegion("mnu")
          val mny    = getRegion("mny")
          val palana = getRegion("palana")
          val pau    = getRegion("pau")
          val piu    = getRegion("piu")
          val porfix = getRegion("porfix")

          val BF      = SpriteManager.EnumTexture.BF.data.texture
          val BW      = SpriteManager.EnumTexture.BW.data.texture
          val itemkar = SpriteManager.EnumTexture.itemkar.data.texture
          val menu    = SpriteManager.EnumTexture.menu.data.texture
          val rules   = SpriteManager.EnumTexture.rules.data.texture
          val sett    = SpriteManager.EnumTexture.sett.data.texture
          val porfix_mask    = SpriteManager.EnumTexture.porfix_mask.data.texture

     }

}