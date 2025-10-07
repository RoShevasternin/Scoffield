package com.puzfortunes.twinst.game.actors

import com.puzfortunes.twinst.game.utils.advanced.AdvancedGroup
import com.puzfortunes.twinst.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}