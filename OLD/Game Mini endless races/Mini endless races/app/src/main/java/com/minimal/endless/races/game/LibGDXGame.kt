package com.minimal.endless.races.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.minimal.endless.races.MainActivity
import com.minimal.endless.races.game.manager.MusicManager
import com.minimal.endless.races.game.manager.NavigationManager
import com.minimal.endless.races.game.manager.ParticleEffectManager
import com.minimal.endless.races.game.manager.SoundManager
import com.minimal.endless.races.game.manager.SpriteManager
import com.minimal.endless.races.game.manager.util.MusicUtil
import com.minimal.endless.races.game.manager.util.ParticleEffectUtil
import com.minimal.endless.races.game.manager.util.SoundUtil
import com.minimal.endless.races.game.manager.util.SpriteUtil
import com.minimal.endless.races.game.screens.MiniLoaderScreen
import com.minimal.endless.races.game.utils.advanced.AdvancedGame
import com.minimal.endless.races.game.utils.disposeAll
import com.minimal.endless.races.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager      : AssetManager      private set
    lateinit var navigationManager : NavigationManager private set
    lateinit var spriteManager     : SpriteManager     private set
    lateinit var musicManager      : MusicManager      private set
    lateinit var soundManager      : SoundManager      private set
    lateinit var particleEffectManager: ParticleEffectManager private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val allAssets    by lazy { SpriteUtil.AllAssets() }
    val loaderAssets by lazy { SpriteUtil.LoaderAssets() }
    val particleEffectUtil by lazy { ParticleEffectUtil() }

    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)
        particleEffectManager = ParticleEffectManager(assetManager)

        navigationManager.navigate(MiniLoaderScreen::class.java.name)
    }

    private val colorBackground = Color.WHITE

    override fun render() {
        ScreenUtils.clear(colorBackground)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(musicUtil, assetManager)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}