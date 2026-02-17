package com.beeand.honeybonanza.game.screens

import android.content.pm.ActivityInfo
import com.beeand.honeybonanza.game.GDX_GLOBAL_isGame
import com.beeand.honeybonanza.game.LibGDXGame
import com.beeand.honeybonanza.game.manager.MusicManager
import com.beeand.honeybonanza.game.manager.ParticleEffectManager
import com.beeand.honeybonanza.game.manager.SoundManager
import com.beeand.honeybonanza.game.manager.SpriteManager
import com.beeand.honeybonanza.game.utils.TIME_ANIM
import com.beeand.honeybonanza.game.utils.actor.animHide
import com.beeand.honeybonanza.game.utils.advanced.AdvancedScreen
import com.beeand.honeybonanza.game.utils.advanced.AdvancedStage
import com.beeand.honeybonanza.game.utils.gdxGame
import com.beeand.honeybonanza.game.utils.region
import com.beeand.honeybonanza.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BeeLoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        //setBackBackground(game.startAssets.YELLOW.region)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() { }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTextureList = mutableListOf(SpriteManager.EnumTexture.YELLOW.data)
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTexture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTextureList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.particleEffectManager) {
            loadableParticleEffectList = ParticleEffectManager.EnumParticleEffect.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlasAndTexture()
        game.musicManager.init()
        game.soundManager.init()
        game.particleEffectManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((5..10).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            toGame()
        }
    }

    private fun toGame() {
        gdxGame.activity.hideWebView()

        game.activity.lottie.hideLoader()
        game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // або PORTRAIT

        game.musicUtil.apply {
            coff  = 0.43f
            music = KORSAKOV.apply { isLooping = true }
        }

        stageUI.root.animHide(TIME_ANIM) {
            game.navigationManager.navigate(BeeMenuScreen::class.java.name)
        }
    }


}