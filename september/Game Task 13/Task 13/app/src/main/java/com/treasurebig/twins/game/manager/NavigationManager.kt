package com.treasurebig.twins.game.manager

import com.badlogic.gdx.Gdx
import com.treasurebig.twins.game.screens.GameScreen
import com.treasurebig.twins.game.screens.MenuScreen
import com.treasurebig.twins.game.screens.RulesScreen
import com.treasurebig.twins.game.screens.LoaderScreen
import com.treasurebig.twins.game.screens.SettingsScreen
import com.treasurebig.twins.game.utils.advanced.AdvancedScreen
import com.treasurebig.twins.game.utils.gdxGame
import com.treasurebig.twins.game.utils.runGDX

class NavigationManager {

    private val backStack = mutableListOf<String>()

    fun navigate(toScreenName: String, fromScreenName: String? = null) = runGDX {
        gdxGame.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back() = runGDX {
        if (isBackStackEmpty()) exit() else gdxGame.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }

    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()
    fun clearBackStack() = backStack.clear()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen  ::class.java.name -> LoaderScreen()
        MenuScreen    ::class.java.name -> MenuScreen()
        RulesScreen   ::class.java.name -> RulesScreen()
        GameScreen    ::class.java.name -> GameScreen()
        SettingsScreen::class.java.name -> SettingsScreen()

        else -> MenuScreen()
    }

}