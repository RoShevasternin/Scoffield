package com.piriamidone.twispinton.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.piriamidone.twispinton.game.manager.AudioManager
import com.piriamidone.twispinton.game.manager.SoundManager
import com.piriamidone.twispinton.game.utils.runGDX

class SoundUtil {

    // Common
    val ClickR   = SoundManager.EnumSound.Click.data.sound
    val POSITIVE = SoundManager.EnumSound.POSITIVE.data.sound
    val NEGATIVE = SoundManager.EnumSound.NEGATIVE.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel / 100f) }
}