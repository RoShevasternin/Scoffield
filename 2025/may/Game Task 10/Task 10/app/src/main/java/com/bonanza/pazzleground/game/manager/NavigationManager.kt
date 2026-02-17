package com.bonanza.pazzleground.game.manager

import com.badlogic.gdx.Gdx
import com.bonanza.pazzleground.game.screens.LoaderScreen
import com.bonanza.pazzleground.game.screens.MenushkaScreen
import com.bonanza.pazzleground.game.screens.PazzleScreen
import com.bonanza.pazzleground.game.screens.RulesScreen
import com.bonanza.pazzleground.game.utils.advanced.AdvancedScreen
import com.bonanza.pazzleground.game.utils.gdxGame
import com.bonanza.pazzleground.game.utils.runGDX

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
        LoaderScreen          ::class.java.name -> LoaderScreen()
        MenushkaScreen         ::class.java.name -> MenushkaScreen()
        RulesScreen         ::class.java.name -> RulesScreen()
        PazzleScreen        ::class.java.name -> PazzleScreen()

        else -> MenushkaScreen()
    }

}