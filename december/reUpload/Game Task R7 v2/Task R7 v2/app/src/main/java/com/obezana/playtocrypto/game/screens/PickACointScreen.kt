package com.obezana.playtocrypto.game.screens

import com.obezana.playtocrypto.game.actors.checkbox.ACheckBox
import com.obezana.playtocrypto.game.actors.checkbox.ACheckBoxGroup
import com.obezana.playtocrypto.game.actors.checkbox.ACheckBoxStyle
import com.obezana.playtocrypto.game.manager.NavigationManager
import com.obezana.playtocrypto.game.manager.SpriteManager
import com.obezana.playtocrypto.game.utils.Layout
import com.obezana.playtocrypto.game.utils.actor.animHide
import com.obezana.playtocrypto.game.utils.actor.animShow
import com.obezana.playtocrypto.game.utils.actor.disable
import com.obezana.playtocrypto.game.utils.actor.setBounds
import com.obezana.playtocrypto.game.utils.advanced.AdvancedScreen
import com.obezana.playtocrypto.game.utils.advanced.AdvancedStage
import com.obezana.playtocrypto.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PickACointScreen: AdvancedScreen() {

    // Actor
    private val aCBList = List(3) { ACheckBox(ACheckBoxStyle.galochka) }


    override fun show() {
        stageUI.root.animHide()
        setUIBackground(SpriteManager.GameRegion.PICKACOINT_BACKGROUND.region)
        super.show()
        stageUI.root.animShow(0.5f)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        mainGroup.disable()
        cblista()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.cblista() {
        val lisL = listOf(
            Layout.LayoutData(422f, 211f, 89f, 84f),
            Layout.LayoutData(741f, 216f, 89f, 84f),
            Layout.LayoutData(1060f, 216f, 89f, 84f),
        )
        val cbgggG = ACheckBoxGroup()
        aCBList.onEachIndexed { index, aCheckBox ->
            addActor(aCheckBox)
            aCheckBox.checkBoxGroup = cbgggG
            aCheckBox.setBounds(lisL[index])

            aCheckBox.setOnCheckListener {
                coroutine.launch {
                    delay(800L)
                    runGDX {
                        stageUI.root.disable()
                        stageUI.root.animHide(0.5f) { NavigationManager.navigate(YrovniScreen(), PickACointScreen()) }
                    }
                }
            }
        }
    }

}