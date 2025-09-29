package lucky.jogodobicho.fan.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.game.LibGDXGame
import lucky.jogodobicho.fan.game.actors.background.ABackgroundWithPanel
import lucky.jogodobicho.fan.game.actors.panel.APanelSettings
import lucky.jogodobicho.fan.game.utils.Layout
import lucky.jogodobicho.fan.game.utils.Time_ANIMATION
import lucky.jogodobicho.fan.game.utils.actor.animShow_Suspend
import lucky.jogodobicho.fan.game.utils.actor.setBounds
import lucky.jogodobicho.fan.game.utils.actor.setOnClickListener
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedStage
import lucky.jogodobicho.fan.game.utils.region
import lucky.jogodobicho.fan.game.utils.runGDX

class A33Screen(override val game: LibGDXGame) : AdvancedScreen() {

    private val backgroundWithPanel = ABackgroundWithPanel(this).apply { color.a = 0f }
    private val panelSettings       = APanelSettings(this)
    private val imgBACKmenu = Image(game.gameAssets.THREE_POINTS)

    override fun show() {
        setBackgrounds(game.splashAssets.MAIN_BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(backgroundWithPanel)
                backgroundWithPanel.tmpGroup.addActors(
                    panelSettings.apply {
                        setBounds(67f, 322f, 946f, 1403f)
                    },
                    imgBACKmenu.apply {
                        setBounds(Layout.General.three_points)
                        setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
                    }
                )
            }
            delay(500)
            backgroundWithPanel.animShow_Suspend(Time_ANIMATION)
        }
    }


}