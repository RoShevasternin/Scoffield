package com.bonanza.sweetcards.game.screens

import android.content.pm.ActivityInfo
import com.bonanza.sweetcards.game.game
import com.bonanza.sweetcards.game.manager.NavigationManager
import com.bonanza.sweetcards.game.manager.SpriteManager
import com.bonanza.sweetcards.game.util.advanced.AdvancedScreen
import com.bonanza.sweetcards.MainActivity
import com.bonanza.sweetcards.game.GDX_GLOBAL_isGame
import com.bonanza.sweetcards.game.util.gdxGame

class LoaderScreen : AdvancedScreen() {

    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        //setBackBackground(SpriteManager.CommonRegion.BACKGROUND.region)
        super.show()

        isFinishProgress = true
    }


    override fun render(delta: Float) {
        super.render(delta)

        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // або PORTRAIT

            gdxGame.activity.hideWebView()
            gdxGame.activity.lottie.hideLoader()
            NavigationManager.navigate(GameScreen())
        }

    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    private fun loadSplashAssets() {
        with(SpriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas._1)
            load(game.assetManager)
        }
        game.assetManager.finishLoading()
        SpriteManager.init(game.assetManager)
    }

}