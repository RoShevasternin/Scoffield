package com.plinko.x10ballsss.game.screens

import com.plinko.x10ballsss.game.GDX_GLOBAL_isGame
import com.plinko.x10ballsss.game.LibGDXGame
import com.plinko.x10ballsss.game.manager.MusicManager
import com.plinko.x10ballsss.game.manager.ParticleEffectManager
import com.plinko.x10ballsss.game.manager.SoundManager
import com.plinko.x10ballsss.game.manager.SpriteManager
import com.plinko.x10ballsss.game.utils.TIME_ANIM
import com.plinko.x10ballsss.game.utils.actor.animHide
import com.plinko.x10ballsss.game.utils.advanced.AdvancedScreen
import com.plinko.x10ballsss.game.utils.advanced.AdvancedStage
import com.plinko.x10ballsss.game.utils.gdxGame
import com.plinko.x10ballsss.game.utils.region
import com.plinko.x10ballsss.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StarLoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.loaderAssets.FARELLO.region)
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
            loadableTextureList = mutableListOf(SpriteManager.EnumTexture.FARELLO.data)
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

                    delay((12..17).shuffled().first().toLong())
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
        //game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // або PORTRAIT

        game.musicUtil.apply {
            coff  = 0.45f
            music = CINEMATIC_UNIVERSE.apply { isLooping = true }
        }

        stageUI.root.animHide(TIME_ANIM) {
            game.navigationManager.navigate(StarGameScreen::class.java.name)
        }
    }


}