package com.fortunetiger.mystictrail.game.screens.menu

import com.fortunetiger.mystictrail.game.screens.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MenuFragmentController(override val gameFragment: MenuFragment) : ComponentController {

    val coroutine = CoroutineScope(Dispatchers.Main)

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
    }
}