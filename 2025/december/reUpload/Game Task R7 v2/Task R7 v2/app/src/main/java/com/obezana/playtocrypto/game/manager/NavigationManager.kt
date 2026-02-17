package com.obezana.playtocrypto.game.manager

import com.badlogic.gdx.Gdx
import com.obezana.playtocrypto.game.game
import com.obezana.playtocrypto.game.utils.advanced.AdvancedScreen
import com.obezana.playtocrypto.game.utils.runGDX

object NavigationManager {

    val backStack = mutableListOf<AdvancedScreen>()
    var key: Int? = null
        private set

    fun navigate(to: AdvancedScreen, from: AdvancedScreen? = null, key: Int? = null) {
        runGDX {
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
        runGDX {
            NavigationManager.key = key

            if (backStack.isEmpty()) exit()
            else game.screen = backStack.removeAt(backStack.lastIndex).javaClass.newInstance()
        }
    }

    fun exit() {
        runGDX {
            backStack.clear()
            game.dispose()
            Gdx.app.exit()
        }
    }

}