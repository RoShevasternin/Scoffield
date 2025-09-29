package lucky.jogodobicho.fan.game.actors.background

import com.badlogic.gdx.scenes.scene2d.ui.Image
import lucky.jogodobicho.fan.game.utils.Layout
import lucky.jogodobicho.fan.game.utils.actor.setBounds
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen

class ABackgroundSplash(override val screen: AdvancedScreen): AdvancedGroup() {

    private val assets = screen.game.splashAssets

    private val babochke = Image(assets.babochke)
    private val cock     = Image(assets.cock)
    private val monkey   = Image(assets.monkey)
    private val ass      = Image(assets.ass)
    private val box      = Image(assets.box)
    private val grass    = Image(assets.grass)

    override fun addActorsOnGroup() {
        addActors(babochke, cock, monkey, ass, box, grass)
        babochke.setBounds(Layout.Background.babochke)
        cock    .setBounds(Layout.Background.cock)
        monkey  .setBounds(Layout.Background.monkey)
        ass     .setBounds(Layout.Background.ass)
        box     .setBounds(Layout.Background.box)
        grass   .setBounds(Layout.Background.grass)
    }

}