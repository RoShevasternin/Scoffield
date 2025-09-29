package lucky.jogodobicho.fan.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.font.FontParameter
import lucky.jogodobicho.fan.game.utils.runGDX

class ATimer(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
    private val font      = screen.fontBDCarton.generateFont(parameter.setCharacters(FontParameter.CharType.NUMBERS.chars+":").setSize(43))

    private val backgroundImage = Image(screen.game.gameAssets.PANEL_TIMER)
    private val timerImage      = Image(screen.game.gameAssets.ICON_TIMER)
    private val timerLabel      = Label("00:60", Label.LabelStyle(font, Color.WHITE))


    override fun addActorsOnGroup() {
        addAndFillActor(backgroundImage)
        addTimerImg()
        addTimerLbl()
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addTimerImg() {
        addActor(timerImage)
        timerImage.setBounds(59f, 4f, 88f, 88f)
        timerImage.setOrigin(Align.center)

        timerImage.addAction(Actions.forever(Actions.sequence(
            Actions.rotateBy(45f, 0.5f),
            Actions.rotateBy(-90f, 1f),
            Actions.rotateBy(45f, 0.5f),
        )))
    }

    private fun AdvancedGroup.addTimerLbl() {
        addActor(timerLabel)
        timerLabel.setBounds(170f, 21f, 149f, 49f)
        timerLabel.setAlignment(Align.center)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun startTimer(finishBlock: () -> Unit) {
        coroutine?.launch {
            var time = 60

            while (isActive && time > 0) {
                delay(1000)
                --time
                runGDX { timerLabel.setText("00:${if (time<10) "0$time" else time}") }
            }

            if (time <= 0) {
                cancel()
                finishBlock()
            }
        }
    }


}