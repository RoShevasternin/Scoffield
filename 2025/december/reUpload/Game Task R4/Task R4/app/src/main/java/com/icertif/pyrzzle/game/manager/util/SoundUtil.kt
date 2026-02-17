package com.icertif.pyrzzle.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.icertif.pyrzzle.game.manager.AudioManager
import com.icertif.pyrzzle.game.manager.SoundManager
import com.icertif.pyrzzle.game.utils.runGDX

class SoundUtil {

    // Common
    val game     = SoundManager.EnumSound.game.data.sound
    val glass    = SoundManager.EnumSound.glass.data.sound
    val negative = SoundManager.EnumSound.negative.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel / 100f) }
}