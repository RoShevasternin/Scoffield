package com.plinki.kollinllabs.game.actors

import com.plinki.kollinllabs.game.utils.advanced.AdvancedGroup
import com.plinki.kollinllabs.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}