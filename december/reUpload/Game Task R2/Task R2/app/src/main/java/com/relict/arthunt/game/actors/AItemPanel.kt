package com.relict.arthunt.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.relict.arthunt.game.utils.advanced.AdvancedGroup
import com.relict.arthunt.game.utils.advanced.AdvancedScreen
import com.relict.arthunt.game.utils.font.FontParameter

class AItemPanel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(48)
    private val font          = screen.fontGenerator_BlackHanSansRegular.generateFont(fontParameter)

    private val itemImg  = Image()
    private val countLbl = Label("0", Label.LabelStyle(font, Color.WHITE))

    var counter = 0
        private set

    override fun addActorsOnGroup() {
        addItemImage()
        addCountLabel()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addItemImage() {
        addActor(itemImg)
        itemImg.setBounds(0f, 1f, 60f, 60f)
    }

    private fun addCountLabel() {
        addActor(countLbl)
        countLbl.setBounds(87f, 0f, 32f, 60f)
        countLbl.setAlignment(Align.center)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun update(count: Int, region: TextureRegion) {
        counter = count
        countLbl.setText(count)
        itemImg.drawable = TextureRegionDrawable(region)
    }

    fun caught() {
        if (counter > 0) {
            counter--
            countLbl.setText(counter)
        }

    }

}