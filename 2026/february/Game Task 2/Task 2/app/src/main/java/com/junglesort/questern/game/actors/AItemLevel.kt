package com.junglesort.questern.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.junglesort.questern.game.utils.GameColor
import com.junglesort.questern.game.utils.actor.HAlign
import com.junglesort.questern.game.utils.actor.VAlign
import com.junglesort.questern.game.utils.actor.addActorAligned
import com.junglesort.questern.game.utils.actor.addActors
import com.junglesort.questern.game.utils.actor.addAndFillActor
import com.junglesort.questern.game.utils.actor.addAndFillActors
import com.junglesort.questern.game.utils.actor.disable
import com.junglesort.questern.game.utils.advanced.AdvancedGroup
import com.junglesort.questern.game.utils.advanced.AdvancedScreen
import com.junglesort.questern.game.utils.font.FontParameter
import com.junglesort.questern.game.utils.gdxGame

class AItemLevel(
    override val screen: AdvancedScreen,
    index: Int,
): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontTitle = screen.fontGenerator_Regular.generateFont(parameter.setSize(104).setBorder(2f, GameColor.border2))

    private val imgPanel = Image(gdxGame.assetsAll.lvl_item)
    private val imgBlock = Image(gdxGame.assetsAll.lbl_block)
    private val lblTitle = Label("${index.inc()}", Label.LabelStyle(fontTitle, Color.WHITE))

    override fun addActorsOnGroup() {
        addAndFillActors(imgPanel, lblTitle, imgBlock)
        lblTitle.setAlignment(Align.center)

        children.forEach { it.disable() }

        imgBlock.color.a = 0f
    }

    fun closed() {
        lblTitle.color.a = 0f
        imgBlock.color.a = 1f
    }

}