package com.junglesort.questern.game.manager.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.junglesort.questern.game.manager.AudioManager
import com.junglesort.questern.game.utils.runGDX
import com.junglesort.questern.util.cancelCoroutinesAll
import com.junglesort.questern.game.manager.MusicManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.div
import kotlin.times

class MusicUtil: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val moodmode = MusicManager.EnumMusic.moodmode.data.music

    // 0..100
    val volumeLevelFlow = MutableStateFlow(AudioManager.volumeLevelPercent)

    var coff = 1f

    private var lastMusic: Music? = null
    var currentMusic: Music?
        get() = lastMusic
        set(value) { runGDX {
            if (value != null) {
                if (lastMusic != value) {
                    lastMusic?.stop()
                    lastMusic = value
                    lastMusic?.volume = (volumeLevelFlow.value / 100f) * coff
                    lastMusic?.play()
                }
            } else {
                lastMusic?.stop()
                lastMusic = null
            }
        } }

    init {
        coroutine.launch { volumeLevelFlow.collect { level -> runGDX { lastMusic?.volume = (level / 100f) * coff } } }
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        lastMusic = null
        currentMusic  = null
    }

}