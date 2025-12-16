package com.hexofun.rundeo.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.hexofun.rundeo.game.manager.AudioManager
import com.hexofun.rundeo.game.utils.actor.setOnClickListener
import com.hexofun.rundeo.game.utils.advanced.AdvancedGroup
import com.hexofun.rundeo.game.utils.advanced.AdvancedScreen

class ASettings(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        private const val ONE_PERCENT = 100f / 11f
        private val currentVolumeLevelPercent = AudioManager.volumeLevelPercent

        // min 0 | max 11
        private var soundCounter = (currentVolumeLevelPercent / ONE_PERCENT).toInt()
        private var musicCounter = (currentVolumeLevelPercent / ONE_PERCENT).toInt()
    }

    private val assets    = screen.game.allAssets
    private val soundUtil = screen.game.soundUtil
    private val musicUtil = screen.game.musicUtil

    private val soundVolumeGroup = AVolume(screen)
    private val musicVolumeGroup = AVolume(screen)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.allAssets.settings))
        addSoundVolumeGroup()
        addMusicVolumeGroup()
        addSoundBtn()
        addMusicBtn()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addMusicVolumeGroup() {
        addActor(musicVolumeGroup)
        musicVolumeGroup.setBounds(230f, 417f, 406f, 48f)

        musicVolumeGroup.update(musicCounter)
        musicUtil.volumeLevelFlow.value = musicCounter * ONE_PERCENT
    }

    private fun addSoundVolumeGroup() {
        addActor(soundVolumeGroup)
        soundVolumeGroup.setBounds(230f, 193f, 406f, 48f)

        soundVolumeGroup.update(soundCounter)
        soundUtil.volumeLevel = soundCounter * ONE_PERCENT
    }

    private fun addMusicBtn() {
        val minus = Actor()
        val plus  = Actor()

        addActors(minus, plus)

        minus.apply {
            setBounds(137f, 387f, 67f, 106f)
            setOnClickListener(soundUtil) {
                if (musicCounter -1 >= 0) {
                    musicCounter--
                    musicVolumeGroup.update(musicCounter)
                    musicUtil.volumeLevelFlow.value = musicCounter * ONE_PERCENT
                }
            }
        }
        plus.apply {
            setBounds(676f, 401f, 67f, 74f)
            setOnClickListener(soundUtil) {
                if (musicCounter +1 <= 11) {
                    musicCounter++
                    musicVolumeGroup.update(musicCounter)
                    musicUtil.volumeLevelFlow.value = musicCounter * ONE_PERCENT
                }
            }
        }
    }


    private fun addSoundBtn() {
        val minus = Actor()
        val plus  = Actor()

        addActors(minus, plus)

        minus.apply {
            setBounds(137f, 165f, 67f, 106f)
            setOnClickListener(soundUtil) {
                if (soundCounter -1 >= 0) {
                    soundCounter--
                    soundVolumeGroup.update(soundCounter)
                    soundUtil.volumeLevel = soundCounter * ONE_PERCENT
                }
            }
        }
        plus.apply {
            setBounds(676f, 178f, 67f, 74f)
            setOnClickListener(soundUtil) {
                if (soundCounter +1 <= 11) {
                    soundCounter++
                    soundVolumeGroup.update(soundCounter)
                    soundUtil.volumeLevel = soundCounter * ONE_PERCENT
                }
            }
        }
    }

}