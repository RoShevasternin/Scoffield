package com.plinko.aviator.slot.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.Array

class SoundManager(var assetManager: AssetManager) {

    var loadableSoundList = mutableListOf<SoundData>()

    fun load() {
        loadableSoundList.onEach { assetManager.load(it.path, Sound::class.java) }
    }

    fun init() {
        loadableSoundList.onEach { it.sound = assetManager[it.path, Sound::class.java] }
        loadableSoundList.clear()
    }

    enum class EnumSound(val data: SoundData) {
        sound_chips(SoundData("sound/sound_chips.mp3")),
        sound_coin(SoundData("sound/sound_coin.mp3")),
        sound_hit1(SoundData("sound/sound_hit1.mp3")),
        sound_hit2(SoundData("sound/sound_hit2.mp3")),
        sound_hit3(SoundData("sound/sound_hit3.mp3")),
        sound_nowin(SoundData("sound/sound_nowin.mp3")),
        sound_result(SoundData("sound/sound_result.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}