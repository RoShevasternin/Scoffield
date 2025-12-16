package com.filermax.detoxer.game.screens

import android.annotation.SuppressLint
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.game.GDX_GLOBAL_isGame
import com.filermax.detoxer.game.game
import com.filermax.detoxer.game.manager.FontTTFManager
import com.filermax.detoxer.game.manager.NavigationManager
import com.filermax.detoxer.game.manager.SpriteManager
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import com.filermax.detoxer.game.utils.advanced.AdvancedScreen
import com.filermax.detoxer.game.utils.gdxGame
import kotlinx.coroutines.*
import com.filermax.detoxer.game.utils.Layout.Splash as LS

@SuppressLint("CustomSplashScreen")
class SplashScreen : AdvancedScreen() {
    private var isFinishProgress = false

    private val logo   by lazy { Image(SpriteManager.SplashRegion.LOGO.region) }
    private val loader by lazy { Image(SpriteManager.SplashRegion.LOADER.region) }


    override fun show() {
        loadSplashAssets()
        super.show()
        loadAssets()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }


    override fun AdvancedGroup.addActorsOnGroup() {
        addLogo()
        addLoader()
    }


    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedGroup.addLogo() {
        addActor(logo)
        logo.apply {
            setBounds(LS.logo)
            addAction(Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(0.5f)
            ))
        }
    }

    private fun AdvancedGroup.addLoader() {
        CoroutineScope(Dispatchers.Main).launch {
        addActor(loader)
        loader.apply {
            setBounds(LS.loader)
            setOrigin(Align.center)
            addAction(Actions.sequence(
                Actions.alpha(0f),
                Actions.parallel(
                    Actions.fadeIn(0.5f),
                    Actions.forever(Actions.rotateBy(-360f, 1f))
                )
            ))
        } }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    private fun loadSplashAssets() {
        with(SpriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.SPLASH)
            loadAtlas(game.assetManager)
        }

        game.assetManager.finishLoading()

        SpriteManager.initAtlas(game.assetManager)
    }

    private fun loadAssets() {
        with(SpriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.toMutableList()
            loadAtlas(game.assetManager)
        }
        with(FontTTFManager) {
            loadableListFont = (
                    FontTTFManager.BoldFont.values +
                    FontTTFManager.ExtraBoldFont.values +
                    FontTTFManager.SemiBoldFont.values +
                    FontTTFManager.MediumFont.values
            ).toMutableList()
            load(game.assetManager)
        }
    }

    private fun loadingAssets() {
        if (game.assetManager.update(17)) {
            initAssets()
            isFinishProgress = true
        }
    }

    private fun initAssets() {
        SpriteManager.initAtlas(game.assetManager)
        FontTTFManager.init(game.assetManager)
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false
            gdxGame.activity.hideWebView()

            NavigationManager.navigate(CleanScreen())
        }
    }

}