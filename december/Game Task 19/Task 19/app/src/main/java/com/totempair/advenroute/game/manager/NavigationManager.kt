package com.totempair.advenroute.game.manager

import com.badlogic.gdx.Gdx
import com.totempair.advenroute.game.screens.BackrScreen
import com.totempair.advenroute.game.screens.IgraScreen
import com.totempair.advenroute.game.screens.LoaderScreen
import com.totempair.advenroute.game.screens.MenuScreen
import com.totempair.advenroute.game.screens.ResultTryScreen
import com.totempair.advenroute.game.screens.ResultWinScreen
import com.totempair.advenroute.game.screens.RulesScreen
import com.totempair.advenroute.game.utils.advanced.AdvancedScreen
import com.totempair.advenroute.game.utils.gdxGame
import com.totempair.advenroute.game.utils.runGDX

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
        LoaderScreen ::class.java.name -> LoaderScreen()
        MenuScreen   ::class.java.name -> MenuScreen()
        RulesScreen  ::class.java.name -> RulesScreen()
        IgraScreen   ::class.java.name -> IgraScreen()
        ResultWinScreen ::class.java.name -> ResultWinScreen()
        ResultTryScreen::class.java.name -> ResultTryScreen()
        BackrScreen::class.java.name -> BackrScreen()

        else -> MenuScreen()
    }

}