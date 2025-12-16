package com.relict.arthunt.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.relict.arthunt.game.manager.SpriteManager

class SpriteUtil {

     class LoadingAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADING.data.atlas.findRegion(name)

          val bik_bak   = getRegion("bik_bak")
          val lodrink   = getRegion("lodrink")
          val parigures = getRegion("parigures")
          val samshit   = getRegion("samshit")

          val BACKICH      = SpriteManager.EnumTexture.BACKICH.data.texture
          val MIKELANDJELO = SpriteManager.EnumTexture.MIKELANDJELO.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val fanera    = getRegion("fanera")
          val hand      = getRegion("hand")
          val muno_a    = getRegion("muno_a")
          val muno_b    = getRegion("muno_b")
          val Oly       = getRegion("Oly")
          val pase_a    = getRegion("pase_a")
          val pase_b    = getRegion("pase_b")
          val porogress = getRegion("porogress")

          val items = List(7) { getRegion("${it.inc()}") }

          val MASKA_FOR_PROGRESS = SpriteManager.EnumTexture.MASKA_FOR_PROGRESS.data.texture
          val OLY_LEVEL          = SpriteManager.EnumTexture.OLY_LEVEL.data.texture
          val OLY_MENU           = SpriteManager.EnumTexture.OLY_MENU.data.texture
          val OLY_RULES          = SpriteManager.EnumTexture.OLY_RULES.data.texture
          val OLY_SETTINGS       = SpriteManager.EnumTexture.OLY_SETTINGS.data.texture
          val OLYVERYBAD         = SpriteManager.EnumTexture.OLYVERYBAD.data.texture
          val OLYVERYNICE        = SpriteManager.EnumTexture.OLYVERYNICE.data.texture

          val a = SpriteManager.EnumTexture.a.data.texture
          val b = SpriteManager.EnumTexture.b.data.texture
          val c = SpriteManager.EnumTexture.c.data.texture

     }

}