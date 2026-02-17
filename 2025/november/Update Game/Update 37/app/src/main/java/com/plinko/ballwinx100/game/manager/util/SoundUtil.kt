package com.plinko.ballwinx100.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.plinko.ballwinx100.game.manager.AudioManager
import com.plinko.ballwinx100.game.manager.SoundManager
import com.plinko.ballwinx100.game.utils.runGDX

class SoundUtil {

    // Common
    val KLAK          = SoundManager.EnumSound.KLAK.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent / 100f

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel) }
}