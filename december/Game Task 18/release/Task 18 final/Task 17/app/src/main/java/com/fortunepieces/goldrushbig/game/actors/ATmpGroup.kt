package com.fortunepieces.goldrushbig.game.actors

import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedGroup
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}