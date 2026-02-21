package com.diceracers.drimmer.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.diceracers.drimmer.game.manager.AudioManager
import com.diceracers.drimmer.game.utils.runGDX
import com.diceracers.drimmer.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.1f)
    val step  = AdvancedSound(SoundManager.EnumSound.step.data.sound, 1f)
    val lose  = AdvancedSound(SoundManager.EnumSound.lose.data.sound, 0.25f)
    val win   = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.25f)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(advancedSound: AdvancedSound) {
        if (isPause.not()) {
            advancedSound.apply {
                sound.play((volumeLevel / 100f) * coff)
            }
        }
    }

    data class AdvancedSound(val sound: Sound, val coff: Float)
}