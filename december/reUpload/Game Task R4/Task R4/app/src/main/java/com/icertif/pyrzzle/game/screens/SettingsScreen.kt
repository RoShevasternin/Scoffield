package com.icertif.pyrzzle.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.icertif.pyrzzle.game.LibGDXGame
import com.icertif.pyrzzle.game.actors.progress.ASettingProgress
import com.icertif.pyrzzle.game.utils.actor.setOnClickListener
import com.icertif.pyrzzle.game.utils.advanced.AdvancedScreen
import com.icertif.pyrzzle.game.utils.advanced.AdvancedStage
import com.icertif.pyrzzle.game.utils.region
import kotlinx.coroutines.launch

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val panelImg = Image(game.allAssets.settings)

    private val soundProgress = ASettingProgress(this)
    private val musicProgress = ASettingProgress(this)


    override fun show() {
        setBackBackground(game.allAssets.bubinka.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelImg()
        addSoundMusicImg()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.setBounds(21f, 408f, 1051f, 1380f)

        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(866f, 1363f, 206f, 206f)
            setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
        }
    }


    private fun AdvancedStage.addSoundMusicImg() {
        addActors(soundProgress, musicProgress)
        // sound
        soundProgress.apply {
            setBounds(248f, 704f, 599f, 83f)

            setProgressPercent(screen.game.soundUtil.volumeLevel)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.soundUtil.volumeLevel = it
                }
            }
        }
        // music
        musicProgress.apply {
            setBounds(248f, 1119f, 599f, 83f)

            setProgressPercent(screen.game.musicUtil.volumeLevelFlow.value)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.musicUtil.volumeLevelFlow.value = it
                }
            }
        }
    }

}