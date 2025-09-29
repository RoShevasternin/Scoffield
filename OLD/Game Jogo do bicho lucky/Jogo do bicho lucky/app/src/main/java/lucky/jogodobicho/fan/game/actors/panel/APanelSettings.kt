package lucky.jogodobicho.fan.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.game.actors.progress.AVolumeRegulator
import lucky.jogodobicho.fan.game.utils.actor.setBounds
import lucky.jogodobicho.fan.game.utils.actor.setOnClickListener
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen

class APanelSettings(override val screen: AdvancedScreen): AdvancedGroup() {

    private val panelImg      = Image(screen.game.gameAssets.PANEL_SETTINGS)
    private val sound         = AVolumeRegulator(screen)
    private val music         = AVolumeRegulator(screen)
    private val xrestImg      = Image(screen.game.gameAssets.DAGGER)


    override fun addActorsOnGroup() {
        addAndFillActor(panelImg)
        addSound()
        addMusicRegulator()

        addActor(xrestImg)
        xrestImg.apply {
            setBounds(361f, -35f, 224f, 224f)
            setOnClickListener(screen.game.soundUtil) { screen.animHideScreen { screen.game.navigationManager.back() } }
        }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addMusicRegulator() {
        addActor(music)
        music.apply {
            setBounds(164f, 630f, 599f, 77f)
            setProgressPercent(screen.game.musicUtil.volumeLevelFlow.value * 100f)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.musicUtil.volumeLevelFlow.value = it / 100f
                }
            }
        }
    }

    private fun addSound() {
        addActor(sound)
        sound.apply {
            setBounds(164f, 251f, 599f, 77f)
            setProgressPercent(screen.game.soundUtil.volumeLevel * 100f)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.soundUtil.volumeLevel = it / 100f
                }
            }
        }
    }

}