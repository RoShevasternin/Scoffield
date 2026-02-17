package com.totempair.advenroute.game.actors

import com.totempair.advenroute.game.utils.advanced.AdvancedGroup
import com.totempair.advenroute.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}