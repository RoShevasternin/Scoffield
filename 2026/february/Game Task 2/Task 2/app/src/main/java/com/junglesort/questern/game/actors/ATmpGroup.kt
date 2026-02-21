package com.junglesort.questern.game.actors

import com.junglesort.questern.game.utils.advanced.AdvancedGroup
import com.junglesort.questern.game.utils.advanced.AdvancedScreen

class ATmpGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun getPrefHeight() = height
    override fun getPrefWidth() = width

    override fun addActorsOnGroup() { }

}