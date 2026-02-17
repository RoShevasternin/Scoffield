package com.plinko.ballwinx100.game.actors.dialog

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinko.ballwinx100.game.actors.button.LevelMoneyPanel
import com.plinko.ballwinx100.game.actors.button.TopIconButton
import com.plinko.ballwinx100.game.manager.GameDataStoreManager
import com.plinko.ballwinx100.game.utils.HEIGHT_UI
import com.plinko.ballwinx100.game.utils.WIDTH_UI
import com.plinko.ballwinx100.game.utils.actor.setOnClickListener
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.launch

class GameOverDialog(
    override val screen: AdvancedScreen,
    ) : AdvancedGroup() {

    private val backgroundAsset = screen.game.mainAssets.GAME_OVER_DIALOG

    var onHomeClick: () -> Unit = {}
    var onNextClick: () -> Unit = {}

    override fun addActorsOnGroup() {
        addDimmedBackground()
        addDialogBackground()
        addMoneyPanel()
        addButtons()
        updateMoney()
    }

    private fun updateMoney() {
        coroutine?.launch {
            val money = GameDataStoreManager.levelMoney()
            GameDataStoreManager.addMoney(money)
        }
    }

    private fun addButtons() {
        val homeButton = TopIconButton(
            screen,
            screen.game.mainAssets.LABEL_BUTTON_HOME,
            screen.game.mainAssets.BACK_BUTTON_2
        )
        homeButton.setBounds(
            WIDTH_UI/2 - homeButton.width - 25f,
            HEIGHT_UI * 0.345f,
            homeButton.width,
            homeButton.height
        )
        homeButton.setOnClickListener(screen.game.soundUtil) {
            onHomeClick()
        }

        val nextButton = TopIconButton(
            screen,
            screen.game.mainAssets.LABEL_BUTTON_NEXT,
            screen.game.mainAssets.BACK_BUTTON_2
        )
        nextButton.setBounds(
            WIDTH_UI/2 + 25f,
            HEIGHT_UI * 0.345f,
            nextButton.width,
            nextButton.height
        )
        nextButton.setOnClickListener(screen.game.soundUtil) {
            onNextClick()
        }

        addActor(homeButton)
        addActor(nextButton)

    }

    private fun addMoneyPanel() {
        val moneyPanel = LevelMoneyPanel(
            screen,
            screen.game.mainAssets.BACK_SCORE
        ).apply {
            setBounds(
                WIDTH_UI/2 - this.width/2,
                HEIGHT_UI*0.51f - this.height/2,
                this.width,
                this.height
            )
        }
        addActor(moneyPanel)
    }

    private fun addDialogBackground() {
        val dialogBackground = Image(backgroundAsset)
        dialogBackground.setBounds(
            WIDTH_UI*0.5f - dialogBackground.width / 2,
            HEIGHT_UI*0.6f - dialogBackground.height / 2,
            dialogBackground.width,
            dialogBackground.height
        )
        addActor(dialogBackground)
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