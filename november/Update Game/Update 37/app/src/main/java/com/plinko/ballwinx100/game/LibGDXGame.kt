package com.plinko.ballwinx100.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.plinko.ballwinx100.Plinko37Activity
import com.plinko.ballwinx100.game.manager.MusicManager
import com.plinko.ballwinx100.game.manager.NavigationManager
import com.plinko.ballwinx100.game.manager.SoundManager
import com.plinko.ballwinx100.game.manager.SpriteManager
import com.plinko.ballwinx100.game.manager.util.MusicUtil
import com.plinko.ballwinx100.game.manager.util.SoundUtil
import com.plinko.ballwinx100.game.manager.util.SpriteUtil
import com.plinko.ballwinx100.game.screens.WelcomeScreen
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGame
import com.plinko.ballwinx100.game.utils.disposeAll
import com.plinko.ballwinx100.util.log

class LibGDXGame(val activity: Plinko37Activity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val mainAssets   by lazy { SpriteUtil.MainAssets() }

    var backgroundColor = Color.BLACK
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)
        navigationManager.navigate(WelcomeScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
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