package com.goldpairen.matchbig.game.actors

import com.goldpairen.matchbig.game.utils.advanced.AdvancedGroup
import com.goldpairen.matchbig.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}