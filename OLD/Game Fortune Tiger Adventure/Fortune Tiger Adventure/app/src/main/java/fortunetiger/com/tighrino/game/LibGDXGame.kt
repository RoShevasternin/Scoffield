package fortunetiger.com.tighrino.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import fortunetiger.com.tighrino.MainActivity
import fortunetiger.com.tighrino.game.manager.MusicManager
import fortunetiger.com.tighrino.game.manager.NavigationManager
import fortunetiger.com.tighrino.game.manager.SoundManager
import fortunetiger.com.tighrino.game.manager.SpriteManager
import fortunetiger.com.tighrino.game.manager.util.MusicUtil
import fortunetiger.com.tighrino.game.manager.util.SoundUtil
import fortunetiger.com.tighrino.game.manager.util.SpriteUtil
import fortunetiger.com.tighrino.game.screens.IncasLoadingScreen
import fortunetiger.com.tighrino.game.utils.advanced.AdvancedGame
import fortunetiger.com.tighrino.game.utils.disposeAll
import fortunetiger.com.tighrino.util.log

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

        navigationManager.navigate(IncasLoadingScreen::class.java.name)
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