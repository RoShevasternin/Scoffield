package lucky.jogodobicho.fan.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import lucky.jogodobicho.fan.game.manager.SpriteManager

class SpriteUtil {

     class SplashAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(name)

          val ass      = getRegion("ass")
          val babochke = getRegion("babochke")
          val box      = getRegion("box")
          val cock     = getRegion("cock")
          val filatov  = getRegion("filatov")
          val grass    = getRegion("grass")
          val karas    = getRegion("karas")
          val monkey   = getRegion("monkey")

          val MAIN_BACKGROUND = SpriteManager.EnumTexture.MAIN_BACKGROUND.data.texture
          val IRON_MAN        = SpriteManager.EnumTexture.IRON_MAN.data.texture
     }

     class GameAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.GAME.data.atlas.findRegion(name)

          val animals = List(9) { getRegion("animal ${it.inc()}") }
          val foods   = List(9) { getRegion("food ${it.inc()}") }

          val CHECK_MARK             = getRegion("check_mark")
          val DAGGER                 = getRegion("dagger")
          val DIRECTION_TO_THE_RIGHT = getRegion("direction_to_the_right")
          val FLATTER_BACKGROUND     = getRegion("flatter_background")
          val FLATTER_ELIPSE         = getRegion("flatter_elipse")
          val FLATTER_LOADINGER      = getRegion("flatter_loadinger")
          val ICON_TIMER             = getRegion("icon_timer")
          val PANEL_TIMER            = getRegion("panel_timer")
          val SIX_TEETH              = getRegion("six_teeth")
          val THREE_POINTS           = getRegion("three_points")

          val CHOOSE_RIGHT   = SpriteManager.EnumTexture.CHOOSE_RIGHT.data.texture
          val FLATTER_BLACK  = SpriteManager.EnumTexture.FLATTER_BLACK.data.texture
          val LOSERAR        = SpriteManager.EnumTexture.LOSERAR.data.texture
          val MENU_PRSE      = SpriteManager.EnumTexture.MENU_PRSE.data.texture
          val PANEL_RULES    = SpriteManager.EnumTexture.PANEL_RULES.data.texture
          val PANEL_SETTINGS = SpriteManager.EnumTexture.PANEL_SETTINGS.data.texture
          val RESULT         = SpriteManager.EnumTexture.RESULT.data.texture
          val WINRAR         = SpriteManager.EnumTexture.WINRAR.data.texture
     }

}