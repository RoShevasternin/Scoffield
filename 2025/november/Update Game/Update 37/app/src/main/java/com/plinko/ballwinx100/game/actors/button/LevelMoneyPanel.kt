package com.plinko.ballwinx100.game.actors.button

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.plinko.ballwinx100.game.manager.GameDataStoreManager
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.font.FontParameter
import com.plinko.ballwinx100.game.utils.runGDX
import kotlinx.coroutines.launch

class LevelMoneyPanel(
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
        addData()
    }

    private fun refreshData(money: Int) {

        coinLabel.remove()

        coinLabel.setText(money.toString())
        coinLabel.layout()

        coinLabel.setBounds(
            this@LevelMoneyPanel.width / 2 - coinLabel.glyphLayout.width / 2,
            this@LevelMoneyPanel.height / 2 - coinLabel.glyphLayout.height / 2,
            coinLabel.glyphLayout.width,
            coinLabel.glyphLayout.height
        )

        addActor(coinLabel)


    }


    private fun addData() {
        coroutine?.launch {
            val money = GameDataStoreManager.levelMoney()

            runGDX{
                refreshData(money)
            }

        }
    }

    override fun getHeight(): Float {
        return backgroundAsset.regionHeight.toFloat()
    }

    override fun getWidth(): Float {
        return backgroundAsset.regionWidth.toFloat()
    }


}