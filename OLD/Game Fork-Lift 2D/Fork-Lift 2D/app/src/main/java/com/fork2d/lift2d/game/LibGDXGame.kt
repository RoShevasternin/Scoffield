package com.fork2d.lift2d.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.fork2d.lift2d.MainActivity
import com.fork2d.lift2d.game.manager.MusicManager
import com.fork2d.lift2d.game.manager.NavigationManager
import com.fork2d.lift2d.game.manager.SoundManager
import com.fork2d.lift2d.game.manager.SpriteManager
import com.fork2d.lift2d.game.manager.util.MusicUtil
import com.fork2d.lift2d.game.manager.util.SoundUtil
import com.fork2d.lift2d.game.manager.util.SpriteUtil
import com.fork2d.lift2d.game.screens.LoaderScreen
import com.fork2d.lift2d.game.utils.advanced.AdvancedGame
import com.fork2d.lift2d.game.utils.disposeAll
import com.fork2d.lift2d.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val allAssets    by lazy { SpriteUtil.AllAssets() }
    val loaderAssets by lazy { SpriteUtil.LoaderAssets() }

    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
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