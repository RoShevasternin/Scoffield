package com.bonanza.pazzleground.game.actors

import com.bonanza.pazzleground.game.utils.advanced.AdvancedGroup
import com.bonanza.pazzleground.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}