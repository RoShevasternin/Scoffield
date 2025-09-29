package jogo.dobicho.games.game.manager

import com.badlogic.gdx.Gdx
import jogo.dobicho.games.game.LibGDXGame
import jogo.dobicho.games.game.screens.LoaderScreen
import jogo.dobicho.games.game.screens.MenuScreen
import jogo.dobicho.games.game.screens.RulesScreen
import jogo.dobicho.games.game.screens.SettingsScreen
import jogo.dobicho.games.game.screens.TileScreen
import jogo.dobicho.games.game.utils.advanced.AdvancedScreen
import jogo.dobicho.games.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null) = runGDX {
        this.key = key

        game.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back(key: Int? = null) = runGDX {
        this.key = key

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeLast()))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen   ::class.java.name -> LoaderScreen(game)
        MenuScreen     ::class.java.name -> MenuScreen(game)
        RulesScreen    ::class.java.name -> RulesScreen(game)
        SettingsScreen ::class.java.name -> SettingsScreen(game)
        TileScreen     ::class.java.name -> TileScreen(game)

        else -> MenuScreen(game)
    }

}