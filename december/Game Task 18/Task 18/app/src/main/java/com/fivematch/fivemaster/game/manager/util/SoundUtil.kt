package com.fivematch.fivemaster.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.fivematch.fivemaster.game.manager.AudioManager
import com.fivematch.fivemaster.game.utils.runGDX
import com.fivematch.fivemaster.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.55f)
    val win   = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.85f)
    val select = AdvancedSound(SoundManager.EnumSound.select.data.sound, 0.73f)

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