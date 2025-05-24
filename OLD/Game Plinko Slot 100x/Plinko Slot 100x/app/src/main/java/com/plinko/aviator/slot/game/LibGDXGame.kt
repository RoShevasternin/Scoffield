package com.plinko.aviator.slot.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.plinko.aviator.slot.MainActivity
import com.plinko.aviator.slot.game.manager.NavigationManager
import com.plinko.aviator.slot.game.manager.SoundManager
import com.plinko.aviator.slot.game.manager.SpriteManager
import com.plinko.aviator.slot.game.manager.util.SoundUtil
import com.plinko.aviator.slot.game.manager.util.SpriteUtil
import com.plinko.aviator.slot.game.screens.LoaderScreen
import com.plinko.aviator.slot.game.utils.advanced.AdvancedGame
import com.plinko.aviator.slot.game.utils.disposeAll
import com.plinko.aviator.slot.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var soundManager     : SoundManager      private set

    val soundUtil by lazy { SoundUtil()    }
    val allAssets by lazy { SpriteUtil.AllAssets() }

    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
    }

    private val backgroundColor = Color.valueOf("2C3E50")

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(assetManager)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}