package com.diceracers.drimmer.game.actors

import com.diceracers.drimmer.game.utils.advanced.AdvancedGroup
import com.diceracers.drimmer.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}