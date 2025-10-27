package com.plinko.ballwinx100.game.screens

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.plinko.ballwinx100.game.LibGDXGame
import com.plinko.ballwinx100.game.manager.MusicManager
import com.plinko.ballwinx100.game.manager.SoundManager
import com.plinko.ballwinx100.game.manager.SpriteManager
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.advanced.AdvancedStage
import com.plinko.ballwinx100.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class WelcomeScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow = MutableStateFlow(0f)
    private var isFinishLoading = false
    private var isFinishProgress = false
    private var isFinishAnim = false

    override fun show() {
        loadSplashAssets()
       uiBackgroundImage.drawable = TextureRegionDrawable(game.mainAssets.BACK_1)

        //val back1 = game.mainAssets.BACK_1
        //setBackgrounds(game.mainAssets.BACK_1)
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
//        addPlay()
        isFinishAnim = true
    }


    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.MAIN.data,
                )
            loadAtlas()
           loadableTextureList = mutableListOf(
               SpriteManager.EnumTexture.BACK_1.data,
               SpriteManager.EnumTexture.BACK_2.data,
               SpriteManager.EnumTexture.BACK_3.data
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initAtlasAndTexture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.values().map { it.data }.toMutableList()
            loadAtlas()
            loadableTextureList = SpriteManager.EnumTexture.values().map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.values().map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.values().map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlasAndTexture()
        game.musicManager.init()
        game.soundManager.init()
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
//                    runGDX {
//                        progressLoader.setProgressPercent(progress.toFloat())
//                        progressLabel.setText("LOADING $progress...")
//                    }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) {
                        delay(2000)
                        isFinishProgress = true
                    }

                    //delay((5..10).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            game.musicUtil.apply { music = MASIKA.apply { isLooping = true } }

            game.navigationManager.navigate(HomeScreen::class.java.name)
        }
    }


}