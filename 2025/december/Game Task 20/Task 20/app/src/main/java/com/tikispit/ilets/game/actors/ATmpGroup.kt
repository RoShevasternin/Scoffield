package com.tikispit.ilets.game.actors

import com.tikispit.ilets.game.utils.advanced.AdvancedGroup
import com.tikispit.ilets.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}