package jogo.dobicho.games.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.onesignal.OneSignal
import jogo.dobicho.games.game.actors.checkbox.ACheckBox
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import jogo.dobicho.games.game.actors.masks.Mask
import jogo.dobicho.games.game.actors.progress.ADerevoProgress
import jogo.dobicho.games.game.utils.advanced.AdvancedGroup
import jogo.dobicho.games.game.utils.advanced.AdvancedScreen
import jogo.dobicho.games.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

var isNotification = true

class ASettingsGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val musicProgress = ADerevoProgress(screen)
    private val soundProgress = ADerevoProgress(screen)
    private val cbNonificatio = ACheckBox(screen, ACheckBox.Static.Type.NOTIFICATION)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.gameAssets.SETTINGS))
        addProgress()
        addCbNotify()
    }

    private fun addProgress() {
        addActors(musicProgress, soundProgress)
        musicProgress.apply {
            setBounds(203f, 498f, 386f, 35f)
            setProgressPercent(screen.game.musicUtil.music!!.volume * 100f)

            coroutine?.launch(Dispatchers.Default) {
                progressPercentFlow.collect {
                    screen.game.musicUtil.volumeLevelFlow.value = it / 100f
                }
            }
        }
        soundProgress.apply {
            setBounds(203f, 716f, 386f, 35f)
            setProgressPercent(screen.game.soundUtil.volumeLevel * 100f)

            coroutine?.launch(Dispatchers.Default) {
                progressPercentFlow.collect {
                    screen.game.soundUtil.volumeLevel = it / 100f
                }
            }
        }
    }

    private fun addCbNotify() {
        addActor(cbNonificatio)
        cbNonificatio.apply {
            setBounds(310f, 204f, 171f, 76f)
            if (isNotification) uncheck(false) else check(false)

            setOnCheckListener {
                isNotification = it.not()
                if (isNotification) OneSignal.User.pushSubscription.optIn() else OneSignal.User.pushSubscription.optOut()
            }
        }
    }

}