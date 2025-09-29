package lucky.jogodobicho.fan.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import lucky.jogodobicho.fan.MainActivity
import lucky.jogodobicho.fan.game.manager.MusicManager
import lucky.jogodobicho.fan.game.manager.NavigationManager
import lucky.jogodobicho.fan.game.manager.SoundManager
import lucky.jogodobicho.fan.game.manager.SpriteManager
import lucky.jogodobicho.fan.game.manager.util.MusicUtil
import lucky.jogodobicho.fan.game.manager.util.SoundUtil
import lucky.jogodobicho.fan.game.manager.util.SpriteUtil
import lucky.jogodobicho.fan.game.screens.LoaderScreen
import lucky.jogodobicho.fan.game.utils.GameColor
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGame
import lucky.jogodobicho.fan.game.utils.disposeAll
import lucky.jogodobicho.fan.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val splashAssets by lazy { SpriteUtil.SplashAssets() }
    val gameAssets   by lazy { SpriteUtil.GameAssets() }

    var backgroundColor = Color.BLACK
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
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