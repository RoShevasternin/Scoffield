package com.secrofthe.lostpaircret.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedGroup
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedScreen
import com.secrofthe.lostpaircret.game.utils.font.FontParameter
import com.secrofthe.lostpaircret.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ATimer(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font77        = screen.fontGenerator_InriaSans_Bold.generateFont(fontParameter.setSize(77))

    private val timeLbl = Label("60", Label.LabelStyle(font77, Color.WHITE))

    var isPause = false

    override fun addActorsOnGroup() {
        addTimer()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addTimer() {
        addAndFillActor(timeLbl)
        timeLbl.setAlignment(Align.center)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun goTime(timeOut: () -> Unit) {
        coroutine?.launch {
            var timer  = 15
            var second: Int

            while (isActive && timer > 0) {
                delay(1000)
                if (isPause.not()) {
                    timer -= 1
                    second = timer % 60

                    runGDX {
                        timeLbl.setText(second)
                        if (timer <= 10) timeLbl.style.fontColor = Color.RED
                    }
                }
            }

            if (timer <= 0) runGDX { timeOut() }
        }
    }

}