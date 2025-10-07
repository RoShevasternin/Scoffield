package com.fortunepieces.goldrushbig.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.fortunepieces.goldrushbig.game.manager.AudioManager
import com.fortunepieces.goldrushbig.game.utils.runGDX
import com.fortunepieces.goldrushbig.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.78f)
    val win   = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.8f)
    val feil   = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.7f)

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