package com.filermax.detoxer.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.filermax.detoxer.game.actors.checkbox.ACheckBox
import com.filermax.detoxer.game.actors.checkbox.ACheckBoxGroup
import com.filermax.detoxer.game.actors.checkbox.ACheckBoxStyle
import com.filermax.detoxer.game.manager.SpriteManager
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import com.filermax.detoxer.game.utils.Layout.ControlPanel as LCP

class ControlPanel(private val defaultType: Type): AdvancedGroup() {

    var onCheckClean  : () -> Unit = { }
    var onCheckBoost  : () -> Unit = { }
    var onCheckBattery: () -> Unit = { }
    var onCheckCooling: () -> Unit = { }

    private val separator    = Image(SpriteManager.CommonRegion.SEPARATOR.region)
    private val checkBoxList = listOf(
        ACheckBox(ACheckBoxStyle.clean),
        ACheckBox(ACheckBoxStyle.boost),
        ACheckBox(ACheckBoxStyle.battery),
        ACheckBox(ACheckBoxStyle.cooling),
    )


    
    override fun sizeChanged() {
        super.sizeChanged()
        if(width > 0 && height > 0) addActorsOnGroup()
    }


    private fun addActorsOnGroup() {
        addSeparator()
        addCheckBoxList()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun addSeparator() {
        addActor(separator)
        separator.setBounds(LCP.separator)
    }

    private fun addCheckBoxList() {
        val cbGroup = ACheckBoxGroup()
        var nx = LCP.checkBox.x

        checkBoxList.onEachIndexed { index, box ->
            addActor(box)
            box.checkBoxGroup = cbGroup
            with(LCP.checkBox) {
                box.setBounds(nx, y, w, h)
                nx += w + hs
            }
            if (defaultType.index == index) box.check()
            box.setOnCheckListener { if (it) when(index) {
                Type.CLEAN.index   -> onCheckClean()
                Type.BOOST.index   -> onCheckBoost()
                Type.BATTERY.index -> onCheckBattery()
                Type.COOLING.index -> onCheckCooling()
            } }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    enum class Type(val index: Int) {
        CLEAN(0), BOOST(1), BATTERY(2), COOLING(3)
    }
}