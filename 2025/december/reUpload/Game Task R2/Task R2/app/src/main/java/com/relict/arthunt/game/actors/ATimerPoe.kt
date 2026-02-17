package com.relict.arthunt.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.relict.arthunt.game.utils.advanced.AdvancedGroup
import com.relict.arthunt.game.utils.advanced.AdvancedScreen
import com.relict.arthunt.game.utils.font.FontParameter
import com.relict.arthunt.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ATimerPoe(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars+":").setSize(100)
    private val font          = screen.fontGenerator_BlackHanSansRegular.generateFont(fontParameter)

    private val timeLbl = Label("60", Label.LabelStyle(font, Color.WHITE))

    var isPause = false

    override fun addActorsOnGroup() {
        //addAndFillActor(Image(screen.game.allAssets.pase_a))
        addTimeLabel()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addTimeLabel() {
        addAndFillActor(timeLbl)
        timeLbl.setAlignment(Align.center)
//        timeLbl.setBounds(78f, 18f, 102f, 29f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun startTimer(timeOutBlock: () -> Unit) {
        coroutine?.launch {
            var timer  = 60
            var minute: Int
            var second: Int

            while (isActive && timer > 0) {
                delay(1000)
                if (isPause.not()) {
                    timer -= 1
                    minute = timer / 60
                    second = timer % 60

                    runGDX {
//                        timeLbl.setText("0$minute:${if (second < 10) "0$second" else second}")
                        timeLbl.setText("${if (second < 10) "0$second" else second}")
                    }
                }
            }

            if (timer <= 0) runGDX { timeOutBlock() }
        }
    }

}