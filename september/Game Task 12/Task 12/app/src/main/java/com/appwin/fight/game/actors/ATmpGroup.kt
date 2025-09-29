package com.appwin.fight.game.actors

import com.appwin.fight.game.utils.advanced.AdvancedGroup
import com.appwin.fight.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}