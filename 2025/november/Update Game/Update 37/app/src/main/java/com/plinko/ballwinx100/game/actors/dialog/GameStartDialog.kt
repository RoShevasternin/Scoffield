package com.plinko.ballwinx100.game.actors.dialog

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinko.ballwinx100.game.actors.button.BetMoneyPanel
import com.plinko.ballwinx100.game.actors.button.LevelMoneyPanel
import com.plinko.ballwinx100.game.actors.button.TopIconButton
import com.plinko.ballwinx100.game.manager.GameDataStoreManager
import com.plinko.ballwinx100.game.manager.GameDataStoreManager.dataStore
import com.plinko.ballwinx100.game.utils.HEIGHT_UI
import com.plinko.ballwinx100.game.utils.WIDTH_UI
import com.plinko.ballwinx100.game.utils.actor.setOnClickListener
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.runGDX
import com.plinko.ballwinx100.util.DataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameStartDialog(
    override val screen: AdvancedScreen,
    ) : AdvancedGroup() {

        private val test = screen.game.mainAssets.BASKET_0
    private val backgroundAsset = screen.game.mainAssets.GAME_START_DIALOG

    var onHomeClick: () -> Unit = {}
    var onPlayClick: () -> Unit = {}

    override fun addActorsOnGroup() {
        addDimmedBackground()
        addDialogBackground()
        addMoneyPanel()
        addChooser()
        addButtons()
    }

    private fun addChooser() {
        val chooserSize = 88f
        val spacer = 15f
        val ballCountStartX = screen.stageUI.width*0.429f
        val ballCountStartY = screen.stageUI.height*0.642f
        val ballCountOne: Actor = Actor().apply {
            this.height = chooserSize
            this.width = chooserSize
            this.x = ballCountStartX
            this.y = ballCountStartY
            this.setOnClickListener {
                updateBallCount(5)
            }
        }

        val ballCountTwo: Actor = Actor().apply {
            this.height = chooserSize
            this.width = chooserSize
            this.x = ballCountStartX + (chooserSize) + spacer
            this.y = ballCountStartY
            this.setOnClickListener {
                updateBallCount(8)
            }
        }

        val ballCountThree: Actor = Actor().apply {
            this.height = chooserSize
            this.width = chooserSize
            this.x = ballCountStartX + (2 * (chooserSize+ spacer))
            this.y = ballCountStartY
            this.setOnClickListener {
                updateBallCount(12)
            }
        }

        val ballCountFour: Actor = Actor().apply {
            this.height = chooserSize
            this.width = chooserSize
            this.x = ballCountStartX + (3 * (chooserSize+ spacer))
            this.y = ballCountStartY
            this.setOnClickListener {
                updateBallCount(15)
            }
        }

        val ballPriceStartX = screen.stageUI.width*0.435f
        val ballPriceStartY = screen.stageUI.height*0.5525f
        val ballPriceOne: Actor = Actor().apply {
            this.height = chooserSize
            this.width = chooserSize
            this.x = ballPriceStartX
            this.y = ballPriceStartY
            this.setOnClickListener {
                updateBallPrice(1)
            }
        }

        val ballPriceTwo: Actor = Actor().apply {
            this.height = chooserSize
            this.width = chooserSize
            this.x = ballPriceStartX + (chooserSize) + spacer
            this.y = ballPriceStartY
            this.setOnClickListener {
                updateBallPrice(2)
            }
        }

        val ballPriceThree: Actor = Actor().apply {
            this.height = chooserSize
            this.width = chooserSize
            this.x = ballPriceStartX + (2 * (chooserSize+ spacer))
            this.y = ballPriceStartY
            this.setOnClickListener {
                updateBallPrice(5)
            }
        }

        val ballPriceFour: Actor = Actor().apply {
            this.height = chooserSize
            this.width = chooserSize
            this.x = ballPriceStartX + (3 * (chooserSize+ spacer))
            this.y = ballPriceStartY
            this.setOnClickListener {
                updateBallPrice(10)
            }
        }

        addActor(ballCountOne)
        addActor(ballCountTwo)
        addActor(ballCountThree)
        addActor(ballCountFour)
        addActor(ballPriceOne)
        addActor(ballPriceTwo)
        addActor(ballPriceThree)
        addActor(ballPriceFour)

    }

    private fun updateBallCount(count: Int) {
        coroutine?.launch {
            GameDataStoreManager.updateBallCount(count)
        }
    }

    private fun updateBallPrice(price: Int) {
        coroutine?.launch {
            GameDataStoreManager.updateBallPrice(price)
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
            coroutine?.launch {
                val money = GameDataStoreManager.money()
                val count = GameDataStoreManager.ballCount()
                val price = GameDataStoreManager.ballPrice()

                val result = money - (count * price)
                if (result < 0) {
                    GameDataStoreManager.updateMoney(0)
                } else {
                    GameDataStoreManager.updateMoney(result)
                }
            }
            onPlayClick()
        }

        addActor(homeButton)
        addActor(nextButton)

    }

    private fun addMoneyPanel() {
        val moneyPanel = BetMoneyPanel(
            screen,
            screen.game.mainAssets.BACK_SCORE
        ).apply {
            setBounds(
                WIDTH_UI/2 - this.width/2,
                HEIGHT_UI*0.475f - this.height/2,
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