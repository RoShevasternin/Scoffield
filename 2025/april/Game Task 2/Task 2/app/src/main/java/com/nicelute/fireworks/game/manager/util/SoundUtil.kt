package com.nicelute.fireworks.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.nicelute.fireworks.game.manager.AudioManager
import com.nicelute.fireworks.game.manager.SoundManager
import com.nicelute.fireworks.game.utils.runGDX
class SoundUtil {

    val F1    = SoundManager.EnumSound.F1.data.sound
    val F2    = SoundManager.EnumSound.F2.data.sound
    val FIRE  = SoundManager.EnumSound.FIRE.data.sound
    val START = SoundManager.EnumSound.START.data.sound

    val fires = listOf(F1, F2)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

