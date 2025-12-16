package com.watermil.terwater.game.actors

import com.watermil.terwater.game.utils.advanced.AdvancedGroup
import com.watermil.terwater.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}