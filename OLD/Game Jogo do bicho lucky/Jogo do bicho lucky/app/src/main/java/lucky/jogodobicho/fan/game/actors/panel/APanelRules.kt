package lucky.jogodobicho.fan.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.font.FontParameter

class APanelRules(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var rulTxt: String? = null

        private const val defRulText = "Welcome!\n" +
                "\n" +
                "It's a simple and addictive game.\n" +
                "\n" +
                "You need to match the right pairs before time runs out.\n" +
                "\n" +
                "If you get the pairs wrong, you lose. "
    }

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font_54   = screen.fontBDCarton.generateFont(parameter.setSize(54))

    private val panelImg  = Image(screen.game.gameAssets.PANEL_RULES)
    private val textLabel = Label(rulTxt ?: defRulText, Label.LabelStyle(font_54, Color.WHITE))


    override fun addActorsOnGroup() {
        addAndFillActor(panelImg)
        addLbl()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addLbl() {
        addActor(textLabel)
        textLabel.apply {
            setBounds(116f, 172f, 698f,     818f)
            setAlignment(Align.center)
            wrap = true
        }
    }

}