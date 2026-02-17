package com.icertif.pyrzzle.game.manager

import com.badlogic.gdx.Gdx
import com.icertif.pyrzzle.game.LibGDXGame
import com.icertif.pyrzzle.game.screens.LevelScreen
import com.icertif.pyrzzle.game.screens.LoadScreen
import com.icertif.pyrzzle.game.screens.MenuScreen
import com.icertif.pyrzzle.game.screens.GameScreen
import com.icertif.pyrzzle.game.screens.ResultScreen
import com.icertif.pyrzzle.game.screens.RulesScreen
import com.icertif.pyrzzle.game.screens.SettingsScreen
import com.icertif.pyrzzle.game.utils.advanced.AdvancedScreen
import com.icertif.pyrzzle.game.utils.runGDX

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

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }


    fun exit() = runGDX { Gdx.app.exit() }

    fun clearBackStack() {
        backStack.clear()
    }

    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoadScreen     ::class.java.name -> LoadScreen(game)
        MenuScreen     ::class.java.name -> MenuScreen(game)
        RulesScreen    ::class.java.name -> RulesScreen(game)
        SettingsScreen ::class.java.name -> SettingsScreen(game)
        LevelScreen    ::class.java.name -> LevelScreen(game)
        GameScreen   ::class.java.name   -> GameScreen(game)
        ResultScreen   ::class.java.name -> ResultScreen(game)

        else -> MenuScreen(game)
    }

}