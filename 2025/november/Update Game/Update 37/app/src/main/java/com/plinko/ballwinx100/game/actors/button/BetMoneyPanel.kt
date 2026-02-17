package com.plinko.ballwinx100.game.actors.button

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.plinko.ballwinx100.game.manager.GameDataStoreManager
import com.plinko.ballwinx100.game.manager.GameDataStoreManager.dataStore
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.font.FontParameter
import com.plinko.ballwinx100.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BetMoneyPanel(
    override val screen: AdvancedScreen,
    private val backgroundAsset: TextureRegion,
) : AdvancedGroup() {

    val fontParameter = FontParameter()
    val labelStyle = Label.LabelStyle(
        screen.fontRegular.generateFont(
            fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars)
                .setSize(74)
        ),
        Color.WHITE
    )

    val coinLabel = Label("0", labelStyle)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            GameDataStoreManager.ballPriceAndCountFlow(screen.game.activity.applicationContext.dataStore)
                .catch { e ->
                    e.printStackTrace()
                }
                .collectLatest { pair ->
                    delay(100)
                    runGDX {
                        refreshData(pair.first, pair.second)
                    }
                }
        }
    }

    private fun refreshData(count: Int, money: Int) {

        coinLabel.remove()

        val sum = count * money

        coinLabel.setText(sum.toString())
        coinLabel.layout()

        coinLabel.setBounds(
            this@BetMoneyPanel.width / 2 - coinLabel.glyphLayout.width / 2,
            this@BetMoneyPanel.height / 2 - coinLabel.glyphLayout.height / 2,
            coinLabel.glyphLayout.width,
            coinLabel.glyphLayout.height
        )

        addActor(coinLabel)


    }

    override fun getHeight(): Float {
        return backgroundAsset.regionHeight.toFloat()
    }

    override fun getWidth(): Float {
        return backgroundAsset.regionWidth.toFloat()
    }


}