package com.plinkray.ioosudmarine.game.manager

import com.badlogic.gdx.Gdx
import com.plinkray.ioosudmarine.game.screens.GameScreen1
import com.plinkray.ioosudmarine.game.screens.GameScreen2
import com.plinkray.ioosudmarine.game.screens.GameScreen3
import com.plinkray.ioosudmarine.game.screens.LoaderScreen
import com.plinkray.ioosudmarine.game.screens.MenuScreen
import com.plinkray.ioosudmarine.game.screens.RulesScreen
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedScreen
import com.plinkray.ioosudmarine.game.utils.gdxGame
import com.plinkray.ioosudmarine.game.utils.runGDX

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
        GameScreen1    ::class.java.name -> GameScreen1()
        GameScreen2    ::class.java.name -> GameScreen2()
        GameScreen3    ::class.java.name -> GameScreen3()

        else -> MenuScreen()
    }

}