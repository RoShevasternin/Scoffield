package com.plinko.aviator.slot.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.plinko.aviator.slot.game.manager.AudioManager
import com.plinko.aviator.slot.game.manager.SoundManager
import com.plinko.aviator.slot.game.utils.runGDX
class SoundUtil {

    // Common
    val sound_chips = SoundManager.EnumSound.sound_chips.data.sound
    val sound_coin = SoundManager.EnumSound.sound_coin.data.sound
    val sound_hit1 = SoundManager.EnumSound.sound_hit1.data.sound
    val sound_hit2 = SoundManager.EnumSound.sound_hit2.data.sound
    val sound_hit3 = SoundManager.EnumSound.sound_hit3.data.sound
    val sound_nowin = SoundManager.EnumSound.sound_nowin.data.sound
    val sound_result = SoundManager.EnumSound.sound_result.data.sound

    val hit = listOf(sound_hit1, sound_hit2, sound_hit3)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

