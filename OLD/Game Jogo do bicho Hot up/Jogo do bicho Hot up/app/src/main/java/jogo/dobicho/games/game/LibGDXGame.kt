package jogo.dobicho.games.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import jogo.dobicho.games.MainActivity
import jogo.dobicho.games.game.manager.MusicManager
import jogo.dobicho.games.game.manager.NavigationManager
import jogo.dobicho.games.game.manager.SoundManager
import jogo.dobicho.games.game.manager.SpriteManager
import jogo.dobicho.games.game.manager.util.MusicUtil
import jogo.dobicho.games.game.manager.util.SoundUtil
import jogo.dobicho.games.game.manager.util.SpriteUtil
import jogo.dobicho.games.game.screens.LoaderScreen
import jogo.dobicho.games.game.utils.GameColor
import jogo.dobicho.games.game.utils.advanced.AdvancedGame
import jogo.dobicho.games.game.utils.disposeAll
import jogo.dobicho.games.util.log

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