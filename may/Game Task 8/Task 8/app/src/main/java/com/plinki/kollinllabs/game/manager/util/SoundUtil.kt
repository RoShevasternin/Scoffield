package com.plinki.kollinllabs.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.plinki.kollinllabs.game.manager.AudioManager
import com.plinki.kollinllabs.game.utils.runGDX
import com.plinki.kollinllabs.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)

    val loss = AdvancedSound(SoundManager.EnumSound.loss.data.sound, 1f)
    val touch  = AdvancedSound(SoundManager.EnumSound.touch.data.sound, 1f)
    val win  = AdvancedSound(SoundManager.EnumSound.win.data.sound, 1f)

    val hit = listOf(touch)

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