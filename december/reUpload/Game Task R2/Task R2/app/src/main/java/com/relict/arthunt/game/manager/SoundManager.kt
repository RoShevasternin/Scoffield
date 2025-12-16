package com.relict.arthunt.game.manager

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
        failuredrumsoundeffect (SoundData("sound/failure-drum-sound-effect.mp3")),
        poimal                 (SoundData("sound/poimal.mp3")),
        popup                  (SoundData("sound/pop-up.mp3")),
        winner                 (SoundData("sound/winner.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}