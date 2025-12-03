package com.fivematch.fivemaster.game.manager

import com.badlogic.gdx.Gdx
import com.fivematch.fivemaster.game.screens.IgraScreen
import com.fivematch.fivemaster.game.screens.LoaderScreen
import com.fivematch.fivemaster.game.screens.MenuScreen
import com.fivematch.fivemaster.game.screens.ResultScreen
import com.fivematch.fivemaster.game.screens.RulesScreen
import com.fivematch.fivemaster.game.utils.advanced.AdvancedScreen
import com.fivematch.fivemaster.game.utils.gdxGame
import com.fivematch.fivemaster.game.utils.runGDX

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
        RulesScreen::class.java.name -> RulesScreen()
        IgraScreen::class.java.name -> IgraScreen()
        ResultScreen::class.java.name -> ResultScreen()

        else -> MenuScreen()
    }

}