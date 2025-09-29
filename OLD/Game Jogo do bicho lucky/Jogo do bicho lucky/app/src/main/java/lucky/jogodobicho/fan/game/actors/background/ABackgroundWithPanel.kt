package lucky.jogodobicho.fan.game.actors.background

import com.badlogic.gdx.scenes.scene2d.ui.Image
import lucky.jogodobicho.fan.game.actors.TmpGroup
import lucky.jogodobicho.fan.game.utils.Layout
import lucky.jogodobicho.fan.game.utils.actor.setBounds
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen

class ABackgroundWithPanel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val assets = screen.game.splashAssets

    private val cock     = Image(assets.cock)
    private val ass      = Image(assets.ass)
    private val box      = Image(assets.box)
    private val grass    = Image(assets.grass)

    val tmpGroup = TmpGroup(screen)

    override fun addActorsOnGroup() {
        addActors(cock, ass, grass)
        cock    .setBounds(Layout.Background.cock)
        ass     .setBounds(Layout.Background.ass)
        grass   .setBounds(Layout.Background.grass)

        addAndFillActor(tmpGroup)
        addActor(box)

        box.setBounds(Layout.Background.box)
    }

}