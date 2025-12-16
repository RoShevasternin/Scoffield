package com.watermil.terwater.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.watermil.terwater.game.manager.AudioManager
import com.watermil.terwater.game.utils.runGDX
import com.watermil.terwater.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.95f)
    val win   = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.5f)
    val fail  = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.5f)

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