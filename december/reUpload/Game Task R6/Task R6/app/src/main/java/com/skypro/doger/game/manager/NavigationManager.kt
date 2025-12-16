package com.skypro.doger.game.manager

import com.badlogic.gdx.Gdx
import com.skypro.doger.game.game
import com.skypro.doger.game.utils.advanced.AdvancedScreen

object NavigationManager {

    val backStack = mutableListOf<AdvancedScreen>()
    var key: Int? = null
        private set

    fun navigate(to: AdvancedScreen, from: AdvancedScreen? = null, key: Int? = null) {
        Gdx.app.postRunnable {
            NavigationManager.key = key

            game.screen = to
            from?.let { f ->
                backStack.filter { it.name == f.name }.onEach { backStack.remove(it) }
                backStack.add(f)
            }
            backStack.filter { it.name == to.name }.onEach { backStack.remove(it) }
        }
    }

    fun back(key: Int? = null) {
        Gdx.app.postRunnable {
            NavigationManager.key = key

            if (backStack.isEmpty()) exit()
            else game.screen = backStack.removeAt(backStack.lastIndex)
        }
    }

    fun exit() {
        Gdx.app.postRunnable {
            backStack.clear()
            game.dispose()
            Gdx.app.exit()
        }
    }

}