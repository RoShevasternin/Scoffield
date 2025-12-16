package com.obezana.playtocrypto.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.obezana.playtocrypto.game.actors.checkbox.ACheckBox
import com.obezana.playtocrypto.game.actors.checkbox.ACheckBoxGroup
import com.obezana.playtocrypto.game.actors.checkbox.ACheckBoxStyle
import com.obezana.playtocrypto.game.manager.NavigationManager
import com.obezana.playtocrypto.game.manager.SpriteManager
import com.obezana.playtocrypto.game.utils.Layout
import com.obezana.playtocrypto.game.utils.actor.animHide
import com.obezana.playtocrypto.game.utils.actor.animShow
import com.obezana.playtocrypto.game.utils.actor.disable
import com.obezana.playtocrypto.game.utils.actor.enable
import com.obezana.playtocrypto.game.utils.actor.setBounds
import com.obezana.playtocrypto.game.utils.actor.setOnClickListener
import com.obezana.playtocrypto.game.utils.advanced.AdvancedScreen
import com.obezana.playtocrypto.game.utils.advanced.AdvancedStage
import com.obezana.playtocrypto.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
var uroven = 1

class YrovniScreen: AdvancedScreen() {

    // Actor
    private val aCBList = List(8) { ACheckBox(ACheckBoxStyle.zamok) }


    override fun show() {
        stageUI.root.animHide()
        setUIBackground(SpriteManager.GameRegion.YROVNI_BACKGROUND.region)
        super.show()
        stageUI.root.animShow(0.5f)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine.launch {
            runGDX {
                mainGroup.disable()
                cblista()
            }

            aCBList.onEach {
                runGDX { it.animShow(0.4f) }
                delay(120)
            }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.cblista() {
        var nnx = 174f
        var nny = 470f

        val lA = List(8) { Actor() }
        aCBList.onEachIndexed { index, aCheckBox ->
            addActors(aCheckBox, lA[index])
            aCheckBox.setBounds(nnx, nny, 286f, 253f)
            lA[index].setBounds(nnx, nny, 286f, 253f)
            nnx += 286f+27f
            if (index.inc()%4 == 0) {
                nnx = 174f
                nny = 130f
            }
            aCheckBox.check()
            aCheckBox.animHide()
            aCheckBox.disable()
            lA[index].disable()
            lA[index].setOnClickListener { stageUI.root.animHide(0.5f) {
                uroven++
                NavigationManager.navigate(LevOneScreen(), YrovniScreen())
            } }
        }

        (if (uroven > 8) 8 else uroven).also { u ->
            aCBList.take(u).onEachIndexed { index, aCheckBox ->
                aCheckBox.uncheck()
                lA[index].enable()
            }
        }
    }

}