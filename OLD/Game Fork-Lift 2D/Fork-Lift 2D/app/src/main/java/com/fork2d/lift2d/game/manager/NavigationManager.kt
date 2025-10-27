package com.fork2d.lift2d.game.manager

import com.badlogic.gdx.Gdx
import com.fork2d.lift2d.game.LibGDXGame
import com.fork2d.lift2d.game.screens.GameScreen
import com.fork2d.lift2d.game.screens.LoaderScreen
import com.fork2d.lift2d.game.utils.advanced.AdvancedScreen
import com.fork2d.lift2d.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null) = runGDX {
        this.key = key

        game.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back(key: Int? = null) = runGDX {
        this.key = key

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeLast()))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen::class.java.name -> LoaderScreen(game)
        GameScreen  ::class.java.name -> GameScreen(game)

        else -> LoaderScreen(game)
    }

}


