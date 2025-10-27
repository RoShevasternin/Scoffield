package com.plinko.ballwinx100.game.actors.bar

import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen

class EmptyGroup(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    init {
        isVisible = false
    }

    override fun isVisible(): Boolean {
        return false
    }

    override fun addActorsOnGroup() {

    }

}