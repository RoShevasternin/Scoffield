package com.triplees.linees.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.triplees.linees.game.utils.advanced.AdvancedGroup
import com.triplees.linees.game.utils.advanced.AdvancedScreen
import com.triplees.linees.game.utils.font.FontParameter
import com.triplees.linees.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ATimerGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font64        = screen.fontGenerator_AlatsiRegular.generateFont(fontParameter.setSize(96))

    private val panel   = Image(screen.game.allAssets.time_panel)
    private val timeLbl = Label("60", Label.LabelStyle(font64, Color.WHITE))

    var isPause = false

    override fun addActorsOnGroup() {
        addAndFillActor(panel)
        addTimer()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addTimer() {
        addActor(timeLbl)
        timeLbl.setBounds(206f, 24f, 116f, 123f)
        timeLbl.setAlignment(Align.center)

    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun startTimer(timeOutBlock: () -> Unit) {
        coroutine?.launch {
            var timer  = 12

            while (isActive && timer > 0) {
                delay(1000)
                if (isPause.not()) {
                    timer -= 1
                    runGDX { timeLbl.setText(timer) }
                }
            }

            if (timer <= 0) runGDX { timeOutBlock() }
        }
    }

}