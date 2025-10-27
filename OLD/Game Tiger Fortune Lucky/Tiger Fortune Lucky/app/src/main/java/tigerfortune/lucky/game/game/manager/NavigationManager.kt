package tigerfortune.lucky.game.game.manager

import com.badlogic.gdx.Gdx
import tigerfortune.lucky.game.game.LibGDXGame
import tigerfortune.lucky.game.game.screens.YellowLevelingScreen
import tigerfortune.lucky.game.game.screens.YellowLoadingScreen
import tigerfortune.lucky.game.game.screens.YellowMenuingScreen
import tigerfortune.lucky.game.game.screens.YellowRulesingScreen
import tigerfortune.lucky.game.game.screens.YellowSettingsingScreen
import tigerfortune.lucky.game.game.utils.advanced.AdvancedScreen
import tigerfortune.lucky.game.game.utils.runGDX

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
        YellowLoadingScreen    ::class.java.name -> YellowLoadingScreen(game)
        YellowMenuingScreen    ::class.java.name -> YellowMenuingScreen(game)
        YellowRulesingScreen   ::class.java.name -> YellowRulesingScreen(game)
        YellowSettingsingScreen::class.java.name -> YellowSettingsingScreen(game)
        YellowLevelingScreen   ::class.java.name -> YellowLevelingScreen(game)

        else -> YellowMenuingScreen(game)
    }

}