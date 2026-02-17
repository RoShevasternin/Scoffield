package com.treasurebig.twins.game.actors

import com.treasurebig.twins.game.utils.advanced.AdvancedGroup
import com.treasurebig.twins.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}