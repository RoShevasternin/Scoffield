package com.diceracers.drimmer.game.screens

import com.diceracers.drimmer.game.GDX_GLOBAL_isGame
import com.diceracers.drimmer.game.actors.AMainLoader
import com.diceracers.drimmer.game.manager.MusicManager
import com.diceracers.drimmer.game.manager.SoundManager
import com.diceracers.drimmer.game.manager.SpriteManager
import com.diceracers.drimmer.game.utils.Block
import com.diceracers.drimmer.game.utils.TIME_ANIM_SCREEN
import com.diceracers.drimmer.game.utils.actor.animHide
import com.diceracers.drimmer.game.utils.advanced.AdvancedScreen
import com.diceracers.drimmer.game.utils.advanced.AdvancedStage
import com.diceracers.drimmer.game.utils.gdxGame
import com.diceracers.drimmer.game.utils.runGDX
import com.diceracers.drimmer.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    val aMain by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAndFillActor(aMain)
    }

    override fun animHide(blockEnd: Block) {
        aMain.animHide(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShow(blockEnd: Block) {}

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND.data,
                SpriteManager.EnumTexture.MASK.data,
                SpriteManager.EnumTexture.loa.data,
                SpriteManager.EnumTexture.BACKGROUND2.data,
                SpriteManager.EnumTexture.BACKGROUND3.data,
            )
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initAtlasAndTexture()
    }

    private fun loadAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(gdxGame.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        gdxGame.spriteManager.initAtlasAndTexture()
        gdxGame.musicManager.init()
        gdxGame.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (gdxGame.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = gdxGame.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    runGDX { aMain.aProgressLoader.progressPercentFlow.value = progress.toFloat() }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((20..65).shuffled().first().toLong())
                    delay((5..10).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            gdxGame.musicUtil.apply { currentMusic = audiocoffee.apply {
                isLooping = true
                coff      = 0.25f
            } }

            animHide { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) }
        }
    }


}