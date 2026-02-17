package com.sandtro.trintity.game.manager

import com.badlogic.gdx.Gdx
import com.sandtro.trintity.game.screens.IgraScreen
import com.sandtro.trintity.game.screens.LoaderScreen
import com.sandtro.trintity.game.screens.MenuScreen
import com.sandtro.trintity.game.screens.ResultTryScreen
import com.sandtro.trintity.game.screens.ResultWinScreen
import com.sandtro.trintity.game.screens.RulesScreen
import com.sandtro.trintity.game.screens.SettScreen
import com.sandtro.trintity.game.utils.advanced.AdvancedScreen
import com.sandtro.trintity.game.utils.gdxGame
import com.sandtro.trintity.game.utils.runGDX

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
        LoaderScreen    ::class.java.name -> LoaderScreen()
        MenuScreen      ::class.java.name -> MenuScreen()
        RulesScreen     ::class.java.name -> RulesScreen()
        SettScreen      ::class.java.name -> SettScreen()
        IgraScreen      ::class.java.name -> IgraScreen()
        ResultWinScreen ::class.java.name -> ResultWinScreen()
        ResultTryScreen ::class.java.name -> ResultTryScreen()

        else -> MenuScreen()
    }

}