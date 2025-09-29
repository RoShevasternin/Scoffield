package com.appwin.fight.game.manager

import com.badlogic.gdx.Gdx
import com.appwin.fight.game.screens.LoaderScreen
import com.appwin.fight.game.screens.MenuScreen
import com.appwin.fight.game.screens.PazzleScreen
import com.appwin.fight.game.screens.RulesScreen
import com.appwin.fight.game.screens.SettingsScreen
import com.appwin.fight.game.screens.WinScreen
import com.appwin.fight.game.utils.advanced.AdvancedScreen
import com.appwin.fight.game.utils.gdxGame
import com.appwin.fight.game.utils.runGDX

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
        LoaderScreen        ::class.java.name -> LoaderScreen()
        MenuScreen          ::class.java.name -> MenuScreen()
        RulesScreen         ::class.java.name -> RulesScreen()
        PazzleScreen        ::class.java.name -> PazzleScreen()
        SettingsScreen      ::class.java.name -> SettingsScreen()
        WinScreen           ::class.java.name -> WinScreen()

        else -> MenuScreen()
    }

}