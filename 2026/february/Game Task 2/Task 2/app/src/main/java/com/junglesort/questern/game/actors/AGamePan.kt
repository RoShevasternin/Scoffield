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
import com.junglesort.questern.game.utils.actor.setBounds
import com.junglesort.questern.game.utils.advanced.AdvancedGroup
import com.junglesort.questern.game.utils.advanced.AdvancedScreen
import com.junglesort.questern.game.utils.font.FontParameter
import com.junglesort.questern.game.utils.gdxGame
import com.junglesort.questern.game.utils.runGDX
import kotlinx.coroutines.launch

class AGamePan(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontTitle = screen.fontGenerator_Regular.generateFont(parameter.setSize(44))

    private val imgPanel = Image(gdxGame.assetsAll.PANEL)
    private val lblTitle = Label("0", Label.LabelStyle(fontTitle, Color.WHITE))

    val timer = ATimer(screen)

    override fun addActorsOnGroup() {
        addAndFillActors(imgPanel)

        addActor(lblTitle)
        lblTitle.setAlignment(Align.center)
        lblTitle.setBounds(518f, 102f, 32f, 33f)

        addActor(timer)
        timer.setBounds(209f, 102f, 74f, 33f)

        coroutine?.launch {
            AGamePanel.GLOBAL_COST_FLOW.collect {
                runGDX { lblTitle.setText(it) }
            }
        }

    }

}