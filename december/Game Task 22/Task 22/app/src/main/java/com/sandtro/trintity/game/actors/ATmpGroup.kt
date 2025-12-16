package com.sandtro.trintity.game.actors

import com.sandtro.trintity.game.utils.advanced.AdvancedGroup
import com.sandtro.trintity.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}