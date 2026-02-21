package com.junglesort.questern.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.junglesort.questern.game.manager.AudioManager
import com.junglesort.questern.game.utils.runGDX
import com.junglesort.questern.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)
    val lose_game = AdvancedSound(SoundManager.EnumSound.lose_game.data.sound, 1f)
    val win_game = AdvancedSound(SoundManager.EnumSound.win_game.data.sound, 1f)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(advancedSound: AdvancedSound, playCoff: Float = 1f) {
        if (isPause.not()) {
            advancedSound.apply {
                sound.play(((volumeLevel / 100f) * coff) * playCoff)
            }
        }
    }

    data class AdvancedSound(val sound: Sound, val coff: Float)
}