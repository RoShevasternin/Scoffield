package com.nicelute.fireworks.game.manager

import com.badlogic.gdx.Gdx
import com.nicelute.fireworks.game.LibGDXGame
import com.nicelute.fireworks.game.screens.NiceFireScreen1
import com.nicelute.fireworks.game.screens.NiceFireScreen2
import com.nicelute.fireworks.game.screens.NiceFireScreen3
import com.nicelute.fireworks.game.screens.NiceFireScreen4
import com.nicelute.fireworks.game.screens.NiceFireScreen5
import com.nicelute.fireworks.game.screens.NiceFireScreen6
import com.nicelute.fireworks.game.screens.NiceLoaderScreen
import com.nicelute.fireworks.game.screens.NiceMenuScreen
import com.nicelute.fireworks.game.utils.advanced.AdvancedScreen
import com.nicelute.fireworks.game.utils.runGDX

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
        NiceLoaderScreen::class.java.name -> NiceLoaderScreen(game)
        NiceMenuScreen  ::class.java.name -> NiceMenuScreen(game)

        NiceFireScreen1::class.java.name -> NiceFireScreen1(game)
        NiceFireScreen2::class.java.name -> NiceFireScreen2(game)
        NiceFireScreen3::class.java.name -> NiceFireScreen3(game)
        NiceFireScreen4::class.java.name -> NiceFireScreen4(game)
        NiceFireScreen5::class.java.name -> NiceFireScreen5(game)
        NiceFireScreen6::class.java.name -> NiceFireScreen6(game)

        else -> NiceMenuScreen(game)
    }

}


