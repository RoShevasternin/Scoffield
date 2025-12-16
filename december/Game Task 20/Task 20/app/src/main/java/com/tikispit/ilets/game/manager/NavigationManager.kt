package com.tikispit.ilets.game.manager

import com.badlogic.gdx.Gdx
import com.tikispit.ilets.game.screens.PuzzleScreen
import com.tikispit.ilets.game.screens.LoaderScreen
import com.tikispit.ilets.game.screens.MenuScreen
import com.tikispit.ilets.game.screens.ResultREDScreen
import com.tikispit.ilets.game.screens.ResultGREENScreen
import com.tikispit.ilets.game.screens.RulesScreen
import com.tikispit.ilets.game.utils.advanced.AdvancedScreen
import com.tikispit.ilets.game.utils.gdxGame
import com.tikispit.ilets.game.utils.runGDX

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
        LoaderScreen      ::class.java.name -> LoaderScreen()
        MenuScreen        ::class.java.name -> MenuScreen()
        RulesScreen       ::class.java.name -> RulesScreen()
        PuzzleScreen      ::class.java.name -> PuzzleScreen()
        ResultGREENScreen ::class.java.name -> ResultGREENScreen()
        ResultREDScreen   ::class.java.name -> ResultREDScreen()

        else -> MenuScreen()
    }

}