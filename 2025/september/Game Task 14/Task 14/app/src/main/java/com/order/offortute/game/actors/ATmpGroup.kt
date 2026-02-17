package com.order.offortute.game.actors

import com.order.offortute.game.utils.advanced.AdvancedGroup
import com.order.offortute.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}