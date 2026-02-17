package com.plinkray.ioosudmarine.game.actors

import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedGroup
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}