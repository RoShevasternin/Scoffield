package lucky.jogodobicho.fan.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.game.LibGDXGame
import lucky.jogodobicho.fan.game.actors.background.ABackgroundWithPanel
import lucky.jogodobicho.fan.game.utils.Time_ANIMATION
import lucky.jogodobicho.fan.game.utils.actor.animShow_Suspend
import lucky.jogodobicho.fan.game.utils.actor.setOnClickListener
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedStage
import lucky.jogodobicho.fan.game.utils.region
import lucky.jogodobicho.fan.game.utils.runGDX

class A11Screen(override val game: LibGDXGame) : AdvancedScreen() {

    private val backgroundWithPanel = ABackgroundWithPanel(this).apply { color.a = 0f }
    private val menuPRSE      = Image(game.gameAssets.MENU_PRSE)

    override fun show() {
        setBackgrounds(game.splashAssets.MAIN_BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(backgroundWithPanel)
                backgroundWithPanel.tmpGroup.addActor(menuPRSE.apply {
                    setBounds(67f, 195f, 946f, 1529f)
                })
                addPistons()
            }
            delay(500)
            backgroundWithPanel.animShow_Suspend(Time_ANIMATION)
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPistons() {
        val clasesNames = listOf(
            A44Screen::class.java.name,
            A22Screen::class.java.name,
            A33Screen::class.java.name,
            "IDI_NAXUI",
        )

        var ny = 1051f
        clasesNames.onEach { cName ->
            Actor().also { actor ->
                addActor(actor)
                actor.setBounds(257f, ny, 562f, 203f)
                actor.setOnClickListener(game.soundUtil) { navigateGo(cName) }

                ny -= 233f
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navigateGo(id: String) {
        animHideScreen { if (id == "IDI_NAXUI") game.navigationManager.exit() else game.navigationManager.navigate(id, this::class.java.name) }
    }


}