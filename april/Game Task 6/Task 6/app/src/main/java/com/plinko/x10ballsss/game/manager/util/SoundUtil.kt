package com.plinko.x10ballsss.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.plinko.x10ballsss.game.manager.AudioManager
import com.plinko.x10ballsss.game.manager.SoundManager
import com.plinko.x10ballsss.game.utils.runGDX
class SoundUtil {


    val BIMS = SoundManager.EnumSound.BIMS.data.sound
    val CRACKING1 = SoundManager.EnumSound.CRACKING1.data.sound
    val CRACKING2 = SoundManager.EnumSound.CRACKING2.data.sound

    val CRC = listOf(CRACKING1, CRACKING2)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

