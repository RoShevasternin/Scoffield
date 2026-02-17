package com.triplees.linees.game.actors

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.triplees.linees.game.actors.checkbox.ACheckBox
import com.triplees.linees.game.utils.advanced.AdvancedGroup
import com.triplees.linees.game.utils.advanced.AdvancedScreen

class AVolume(override val screen: AdvancedScreen): AdvancedGroup() {

    private val volumes = List(11) { ACheckBox(screen, ACheckBox.Static.Type.VALUE) }

    override fun addActorsOnGroup() {
        addVolumes()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addVolumes() {
        var nx = 0f

        volumes.onEach {
            touchable = Touchable.disabled
            addActor(it)
            it.setBounds(nx, 0f, 35f, 48f)
            nx += 38f
        }
    }

    fun update(num: Int) {
        volumes.onEach { it.uncheck() }
        repeat(num) { volumes[it].check() }
    }

}