package com.fortunetiger.mystictrail.game

import com.fortunetiger.mystictrail.game.screens.menu.ComponentController

interface Disposabler : Disposable {
    val contrOller: ComponentController

    override fun dispose() {
        contrOller.dispose()
    }
}


interface Disposable {
    fun dispose() {}
}