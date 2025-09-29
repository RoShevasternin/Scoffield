package lucky.jogodobicho.fan.game.actors.background

import com.badlogic.gdx.scenes.scene2d.ui.Image
import lucky.jogodobicho.fan.game.actors.TmpGroup
import lucky.jogodobicho.fan.game.utils.Layout
import lucky.jogodobicho.fan.game.utils.actor.setBounds
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen

class ABackgroundGame(override val screen: AdvancedScreen): AdvancedGroup() {

    private val assets = screen.game.splashAssets

    private val grass    = Image(assets.grass)

    val tmpGroup = TmpGroup(screen)


    override fun addActorsOnGroup() {
        addAndFillActor(tmpGroup)
        addActor(grass)
        grass   .setBounds(Layout.Background.grass)
    }

}