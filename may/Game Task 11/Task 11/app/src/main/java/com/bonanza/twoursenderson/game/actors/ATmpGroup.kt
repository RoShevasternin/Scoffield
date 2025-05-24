package com.bonanza.twoursenderson.game.actors

import com.bonanza.twoursenderson.game.utils.advanced.AdvancedGroup
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}