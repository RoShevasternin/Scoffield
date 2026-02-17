package com.hexofun.rundeo.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.hexofun.rundeo.game.manager.AudioManager
import com.hexofun.rundeo.game.manager.SoundManager
import com.hexofun.rundeo.game.utils.runGDX
class SoundUtil {

    val cartoon_jump  = SoundManager.EnumSound.cartoon_jump.data.sound
    val paper_collect = SoundManager.EnumSound.paper_collect.data.sound
    val star          = SoundManager.EnumSound.star.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

