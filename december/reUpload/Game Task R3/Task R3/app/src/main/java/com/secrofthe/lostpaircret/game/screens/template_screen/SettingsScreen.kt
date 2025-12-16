package com.secrofthe.lostpaircret.game.screens.common

import com.secrofthe.lostpaircret.game.LibGDXGame
import com.secrofthe.lostpaircret.game.actors.progress.ASettingsProgress
import com.secrofthe.lostpaircret.game.screens.template_screen.ISuperScreen
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class SettingsScreen(_game: LibGDXGame): ISuperScreen(_game, Static.TypeScreen.Setting) {

    private val soundProgress by lazy { ASettingsProgress(this) }
    private val musicProgress by lazy { ASettingsProgress(this) }

    override fun AdvancedStage.addActorsOnStage() {
        addSoundMusicImg()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addSoundMusicImg() {
        addActors(soundProgress, musicProgress)
        // sound
        soundProgress.apply {
            setBounds(274f, 1020f, 536f, 110f)

            setProgressPercent(screen.game.soundUtil.volumeLevel)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.soundUtil.volumeLevel = it
                }
            }
        }
        // music
        musicProgress.apply {
            setBounds(274f, 683f, 536f, 110f)

            setProgressPercent(screen.game.musicUtil.volumeLevelFlow.value)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.musicUtil.volumeLevelFlow.value = it
                }
            }
        }
    }

}