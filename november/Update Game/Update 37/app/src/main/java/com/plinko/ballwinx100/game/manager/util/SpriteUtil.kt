package com.plinko.ballwinx100.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.plinko.ballwinx100.game.manager.SpriteManager
import com.plinko.ballwinx100.game.utils.region

class SpriteUtil {

     class MainAssets {
          protected fun getRegion(enumAtlas: SpriteManager.EnumAtlas, name: String): TextureRegion = enumAtlas.data.atlas.findRegion(name)

          val SETTINGS_DIALOG = getRegion(SpriteManager.EnumAtlas.MAIN, "settings_dialog")
          val GAME_OVER_DIALOG = getRegion(SpriteManager.EnumAtlas.MAIN, "game_over_dialog")
          val GAME_START_DIALOG = getRegion(SpriteManager.EnumAtlas.MAIN, "game_start_dialog")
          val SWITCH_MUSIC_OFF = getRegion(SpriteManager.EnumAtlas.MAIN, "switch_music_off")
          val SWITCH_MUSIC_ON = getRegion(SpriteManager.EnumAtlas.MAIN, "switch_music_on")
          val SWITCH_SOUND_OFF = getRegion(SpriteManager.EnumAtlas.MAIN, "switch_sound_off")
          val SWITCH_SOUND_ON = getRegion(SpriteManager.EnumAtlas.MAIN, "switch_sound_on")
          val LABEL_MENU_PLAY = getRegion(SpriteManager.EnumAtlas.MAIN, "label_menu_play")
          val LABEL_MENU_SETTINGS = getRegion(SpriteManager.EnumAtlas.MAIN, "label_menu_settings")
          val LABEL_MENU_ABOUT = getRegion(SpriteManager.EnumAtlas.MAIN, "label_menu_about")
          val LABEL_MENU_EXIT = getRegion(SpriteManager.EnumAtlas.MAIN, "label_menu_exit")
          val LABEL_BUTTON_NEXT = getRegion(SpriteManager.EnumAtlas.MAIN, "label_button_next")
          val LABEL_BUTTON_RESTART = getRegion(SpriteManager.EnumAtlas.MAIN, "label_button_restart")
          val LABEL_BUTTON_HOME = getRegion(SpriteManager.EnumAtlas.MAIN, "label_button_home")
          val BACK_BUTTON_1 = getRegion(SpriteManager.EnumAtlas.MAIN, "back_button_1")
          val BACK_BUTTON_2 = getRegion(SpriteManager.EnumAtlas.MAIN, "back_button_2")
          val BALL_0 = getRegion(SpriteManager.EnumAtlas.MAIN, "ball_0")
          val BALL_1 = getRegion(SpriteManager.EnumAtlas.MAIN, "ball_1")
          val BALL_2 = getRegion(SpriteManager.EnumAtlas.MAIN, "ball_2")
          val BALL_3 = getRegion(SpriteManager.EnumAtlas.MAIN, "ball_3")
          val BALL_4 = getRegion(SpriteManager.EnumAtlas.MAIN, "ball_4")
          val BALL_5 = getRegion(SpriteManager.EnumAtlas.MAIN, "ball_5")
          val BASKET_0 = getRegion(SpriteManager.EnumAtlas.MAIN, "basket_0")
          val BASKET_1 = getRegion(SpriteManager.EnumAtlas.MAIN, "basket_1")
          val BASKET_2 = getRegion(SpriteManager.EnumAtlas.MAIN, "basket_2")
          val BASKET_3 = getRegion(SpriteManager.EnumAtlas.MAIN, "basket_3")
          val BUTTON_HOME = getRegion(SpriteManager.EnumAtlas.MAIN, "button_home")
          val BACK_SCORE = getRegion(SpriteManager.EnumAtlas.MAIN, "back_score")
          val IMAGE_PLATFORM = getRegion(SpriteManager.EnumAtlas.MAIN, "image_platform")
          val EMPTY = getRegion(SpriteManager.EnumAtlas.MAIN, "empty")


          val BACK_1 = SpriteManager.EnumTexture.BACK_1.data.texture.region
          val BACK_2 = SpriteManager.EnumTexture.BACK_2.data.texture.region
          val BACK_3 = SpriteManager.EnumTexture.BACK_3.data.texture.region
     }

}