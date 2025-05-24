package com.plinko.aviator.slot.game.screens

import com.badlogic.gdx.audio.Music
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.plinko.aviator.slot.game.LibGDXGame
import com.plinko.aviator.slot.game.manager.SoundManager
import com.plinko.aviator.slot.game.manager.SpriteManager
import com.plinko.aviator.slot.game.utils.TIME_ANIM_ALPHA
import com.plinko.aviator.slot.game.utils.actor.animHide
import com.plinko.aviator.slot.game.utils.advanced.AdvancedScreen
import com.plinko.aviator.slot.game.utils.advanced.AdvancedStage
import com.plinko.aviator.slot.util.log

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    override fun show() {
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
        isFinishAnim = true
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.values().map { it.data }.toMutableList()
            loadAtlas()
            loadableTextureList = SpriteManager.EnumTexture.values().map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.values().map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlasAndTexture()
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
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((10..15).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            game.activity.lottie.hideLoader()

            game.assetManager.load("music.ogg", Music::class.java)
            game.assetManager.finishLoading()
            game.assetManager["music.ogg", Music::class.java].apply {
                isLooping = true
                volume    = 0.25f
                play()
            }

            stageUI.root.animHide(TIME_ANIM_ALPHA) {
                game.navigationManager.navigate(GameScreen::class.java.name)
            }
        }
    }


}