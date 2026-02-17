package com.plinko.ballwinx100.game.manager

import com.badlogic.gdx.Gdx
import com.plinko.ballwinx100.game.LibGDXGame
import com.plinko.ballwinx100.game.screens.game.GameScreen
import com.plinko.ballwinx100.game.screens.HomeScreen
import com.plinko.ballwinx100.game.screens.WelcomeScreen
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null, args: Map<String, Any> = hashMapOf()) = runGDX {
        this.key = key
        val newScreen = getScreenByName(toScreenName, args)
        game.updateScreen(newScreen)
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

    private fun getScreenByName(name: String, args: Map<String, Any> = hashMapOf()): AdvancedScreen = when (name) {
        WelcomeScreen::class.java.name -> WelcomeScreen(game)
        HomeScreen::class.java.name -> HomeScreen(game)
        GameScreen::class.java.name -> GameScreen(game)
        else -> WelcomeScreen(game)
    }

}