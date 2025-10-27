package tigerfortune.lucky.game.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import tigerfortune.lucky.game.MainActivity
import tigerfortune.lucky.game.game.manager.MusicManager
import tigerfortune.lucky.game.game.manager.NavigationManager
import tigerfortune.lucky.game.game.manager.SoundManager
import tigerfortune.lucky.game.game.manager.SpriteManager
import tigerfortune.lucky.game.game.manager.util.MusicUtil
import tigerfortune.lucky.game.game.manager.util.SoundUtil
import tigerfortune.lucky.game.game.manager.util.SpriteUtil
import tigerfortune.lucky.game.game.screens.YellowLoadingScreen
import tigerfortune.lucky.game.game.utils.advanced.AdvancedGame
import tigerfortune.lucky.game.game.utils.disposeAll
import tigerfortune.lucky.game.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil     by lazy { MusicUtil()    }
    val soundUtil     by lazy { SoundUtil()    }
    val loadingAssets by lazy { SpriteUtil.SplashAssets() }
    val allAssets     by lazy { SpriteUtil.GameAssets() }

    var backgroundColor = Color.BLACK
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(YellowLoadingScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(musicUtil, assetManager)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}