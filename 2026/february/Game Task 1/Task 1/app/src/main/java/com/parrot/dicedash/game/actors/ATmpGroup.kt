package com.parrot.dicedash.game.actors

import com.parrot.dicedash.game.utils.advanced.AdvancedGroup
import com.parrot.dicedash.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}