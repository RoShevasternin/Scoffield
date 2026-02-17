package com.goldpairen.matchbig.game.manager

import com.badlogic.gdx.Gdx
import com.goldpairen.matchbig.game.screens.GameScreen
import com.goldpairen.matchbig.game.screens.MenuScreen
import com.goldpairen.matchbig.game.screens.RulesScreen
import com.goldpairen.matchbig.game.screens.LoaderScreen
import com.goldpairen.matchbig.game.screens.SettingsScreen
import com.goldpairen.matchbig.game.screens.WinScreen
import com.goldpairen.matchbig.game.utils.advanced.AdvancedScreen
import com.goldpairen.matchbig.game.utils.gdxGame
import com.goldpairen.matchbig.game.utils.runGDX

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
        WinScreen     ::class.java.name -> WinScreen()

        else -> MenuScreen()
    }

}