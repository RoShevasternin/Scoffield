package com.plinko.ballwinx100.game.actors.button

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.plinko.ballwinx100.game.manager.GameDataStoreManager
import com.plinko.ballwinx100.game.manager.GameDataStoreManager.dataStore
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.font.FontParameter
import com.plinko.ballwinx100.game.utils.runGDX
import com.plinko.ballwinx100.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoneyPanel(
    override val screen: AdvancedScreen,
    private val backgroundAsset: TextureRegion,
) : AdvancedGroup() {

    val fontParameter = FontParameter()
    val labelStyle = Label.LabelStyle(
        screen.fontRegular.generateFont(
            fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars)
                .setSize(54)
        ),
        Color.WHITE
    )

    val coinLabel = Label("0", labelStyle)

    override fun addActorsOnGroup() {
        addBackground()
        addData()
        coroutine?.launch {
            GameDataStoreManager.moneyFlow(screen.game.activity.applicationContext.dataStore)
                .catch { e ->
                    log("Error fetching money: ${e.message}")
                    e.printStackTrace()
                }
                .collectLatest { money ->
                    delay(100)
                    runGDX {
                        refreshData(money)
                    }
                }
        }
    }

    private fun refreshData(money: Int) {

        coinLabel.remove()

        coinLabel.setText(money.toString())
        coinLabel.layout()

        coinLabel.setBounds(
            this@MoneyPanel.width / 2 - coinLabel.glyphLayout.width / 2,
            this@MoneyPanel.height / 2 - coinLabel.glyphLayout.height / 2,
            coinLabel.glyphLayout.width,
            coinLabel.glyphLayout.height
        )

        addActor(coinLabel)


    }


    private fun addData() {
        coroutine?.launch {
            val money = GameDataStoreManager.money()

            runGDX{
                refreshData(money)
            }

        }
    }

    private fun addBackground() {
        val backgroundImage = Image(backgroundAsset)
        backgroundImage.width = this.width
        backgroundImage.height = this.height
        addAndFillActor(backgroundImage)
    }

    override fun getHeight(): Float {
        return backgroundAsset.regionHeight.toFloat()
    }

    override fun getWidth(): Float {
        return backgroundAsset.regionWidth.toFloat()
    }


}