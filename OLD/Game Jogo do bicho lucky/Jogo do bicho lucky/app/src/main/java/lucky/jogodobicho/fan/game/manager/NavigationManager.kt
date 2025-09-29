package lucky.jogodobicho.fan.game.manager

import com.badlogic.gdx.Gdx
import lucky.jogodobicho.fan.game.LibGDXGame
import lucky.jogodobicho.fan.game.screens.A11Screen
import lucky.jogodobicho.fan.game.screens.A22Screen
import lucky.jogodobicho.fan.game.screens.A33Screen
import lucky.jogodobicho.fan.game.screens.A44Screen
import lucky.jogodobicho.fan.game.screens.LoaderScreen
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.runGDX

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
        LoaderScreen ::class.java.name -> LoaderScreen(game)
        A11Screen    ::class.java.name -> A11Screen(game)
        A22Screen    ::class.java.name -> A22Screen(game)
        A33Screen    ::class.java.name -> A33Screen(game)
        A44Screen    ::class.java.name -> A44Screen(game)

        else -> A11Screen(game)
    }

}