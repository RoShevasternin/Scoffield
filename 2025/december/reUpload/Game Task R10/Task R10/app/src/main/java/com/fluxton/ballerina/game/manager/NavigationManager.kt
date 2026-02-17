package com.fluxton.ballerina.game.manager

import com.badlogic.gdx.Gdx
import com.fluxton.ballerina.game.LibGDXGame
import com.fluxton.ballerina.game.screens.PinkMenuScreen
import com.fluxton.ballerina.game.screens.PinkRulesScreen
import com.fluxton.ballerina.game.screens.PinkLoaderScreen
import com.fluxton.ballerina.game.screens.PinkSettingsScreen
import com.fluxton.ballerina.game.screens.PinkYrowniScreen
import com.fluxton.ballerina.game.screens.ResultScreen
import com.fluxton.ballerina.game.screens.levels._1_Screen
import com.fluxton.ballerina.game.screens.levels._2_Screen
import com.fluxton.ballerina.game.screens.levels._3_Screen
import com.fluxton.ballerina.game.screens.levels._4_Screen
import com.fluxton.ballerina.game.utils.advanced.AdvancedScreen
import com.fluxton.ballerina.game.utils.runGDX

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

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        PinkLoaderScreen   ::class.java.name -> PinkLoaderScreen(game)
        PinkMenuScreen     ::class.java.name -> PinkMenuScreen(game)
        PinkRulesScreen    ::class.java.name -> PinkRulesScreen(game)
        PinkSettingsScreen ::class.java.name -> PinkSettingsScreen(game)
        PinkYrowniScreen   ::class.java.name -> PinkYrowniScreen(game)
        ResultScreen       ::class.java.name -> ResultScreen(game)

        // ilevel
        _1_Screen::class.java.name -> _1_Screen(game)
        _2_Screen::class.java.name -> _2_Screen(game)
        _3_Screen::class.java.name -> _3_Screen(game)
        _4_Screen::class.java.name -> _4_Screen(game)

        else -> PinkMenuScreen(game)
    }

}


