package com.bonanza.twoursenderson.game.manager

import com.badlogic.gdx.Gdx
import com.bonanza.twoursenderson.game.screens.LoaderScreen
import com.bonanza.twoursenderson.game.screens.MenuScreen
import com.bonanza.twoursenderson.game.screens.GameScreen
import com.bonanza.twoursenderson.game.screens.RulesScreen
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedScreen
import com.bonanza.twoursenderson.game.utils.gdxGame
import com.bonanza.twoursenderson.game.utils.runGDX

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
        MenuScreen         ::class.java.name -> MenuScreen()
        RulesScreen         ::class.java.name -> RulesScreen()
        GameScreen        ::class.java.name -> GameScreen()

        else -> MenuScreen()
    }

}