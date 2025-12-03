package com.fivematch.fivemaster.game.actors

import com.fivematch.fivemaster.game.utils.advanced.AdvancedGroup
import com.fivematch.fivemaster.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}