package com.fluxton.ballerina.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.fluxton.ballerina.game.manager.SpriteManager

class SpriteUtil {

     class StartAssets {
          val _3 = SpriteManager.EnumTexture._3.data.texture
          val leo = SpriteManager.EnumTexture.leo.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getPath(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          val ball      = getRegion("ball")
          val joystick  = getRegion("joystick")
          val men_def   = getRegion("men_def")
          val men_press = getRegion("men_press")
          val off       = getRegion("off")
          val on        = getRegion("on")
          val panel     = getRegion("panel")
          val pus_check = getRegion("pus_check")
          val pus_def   = getRegion("pus_def")
          val coin      = getRegion("coin")

          val listT = List(4) { getRegion("t${it.inc()}") }
          val listP = List(4) { getPath("p${it.inc()}") }

          val _1       = SpriteManager.EnumTexture._1.data.texture
          val _2       = SpriteManager.EnumTexture._2.data.texture
          val _4       = SpriteManager.EnumTexture._4.data.texture
          val lose     = SpriteManager.EnumTexture.lose.data.texture
          val maps     = SpriteManager.EnumTexture.maps.data.texture
          val menu     = SpriteManager.EnumTexture.menu.data.texture
          val rules    = SpriteManager.EnumTexture.rules.data.texture
          val settings = SpriteManager.EnumTexture.settings.data.texture
          val win      = SpriteManager.EnumTexture.win.data.texture
     }

}