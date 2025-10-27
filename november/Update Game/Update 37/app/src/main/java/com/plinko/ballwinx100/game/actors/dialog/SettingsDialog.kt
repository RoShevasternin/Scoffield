package com.plinko.ballwinx100.game.actors.dialog

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinko.ballwinx100.game.actors.ASetering
import com.plinko.ballwinx100.game.actors.bar.TopBar
import com.plinko.ballwinx100.game.actors.button.TopIconButton
import com.plinko.ballwinx100.game.utils.HEIGHT_UI
import com.plinko.ballwinx100.game.utils.WIDTH_UI
import com.plinko.ballwinx100.game.utils.actor.setOnClickListener
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen

class SettingsDialog(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val setering = ASetering(screen)

    private val backgroundAsset = screen.game.mainAssets.SETTINGS_DIALOG

    var onHomeClick: () -> Unit = {}


    override fun addActorsOnGroup() {
        addDimmedBackground()
        addTopBar()
        addDialogBackground()
        addSwitches()
    }

    private fun addSwitches() {
        setering.setBounds(
            screen.stageUI.width / 2 - setering.width / 2,
            screen.stageUI.height*0.355f,
            setering.width, setering.height
        )
        addActor(setering)
    }

    private fun addDialogBackground() {
        val dialogBackground = Image(backgroundAsset)
        dialogBackground.setBounds(
            WIDTH_UI / 2 - dialogBackground.width / 2,
            HEIGHT_UI / 2 - dialogBackground.height / 2,
            dialogBackground.width,
            dialogBackground.height
        )
        addActor(dialogBackground)
    }


    private fun addTopBar() {
        val topBar = TopBar(
            screen,
            startTexture = TopIconButton(
                screen,
                screen.game.mainAssets.EMPTY,
                screen.game.mainAssets.BUTTON_HOME
            ).apply {
                this.setOnClickListener(screen.game.soundUtil) {
                    onHomeClick()
                }
            },
        ).apply {
            setBounds(
                0f,
                HEIGHT_UI - this.height - 32f,
                this.width,
                this.height
            )
        }

        addActor(topBar)
    }


    private fun addDimmedBackground() {
        val backgroundRed = Image(
            screen.drawerUtil.getRegion(
                Color.valueOf("000000").apply { a = 0.80f }
            )
        )
        addAndFillActor(backgroundRed)
    }


}