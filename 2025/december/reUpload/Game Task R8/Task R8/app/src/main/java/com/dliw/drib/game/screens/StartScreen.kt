package com.dliw.drib.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.dliw.drib.game.GDX_GLOBAL_isGame
import com.dliw.drib.game.GDX_GLOBAL_isLoadAssets
import com.dliw.drib.game.LibGDXGame
import com.dliw.drib.game.manager.MusicManager
import com.dliw.drib.game.manager.SoundManager
import com.dliw.drib.game.manager.SpriteManager
import com.dliw.drib.game.utils.TIME_ANIM
import com.dliw.drib.game.utils.actor.animHide
import com.dliw.drib.game.utils.advanced.AdvancedScreen
import com.dliw.drib.game.utils.advanced.AdvancedStage
import com.dliw.drib.game.utils.region
import com.dliw.drib.util.gdxGame
import com.dliw.drib.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StartScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        //setBackBackground(game.startAssets.BACKGROUND.region)
        super.show()

        stageUI.addActor(Image(gdxGame.startAssets.LOAD).also {
            it.setBounds(703f, 283f, 513f, 513f)
            it.setOrigin(Align.center)
            it.addAction(Actions.forever(Actions.rotateBy(-360f, 1.2f)))
        })

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
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND.data,
                SpriteManager.EnumTexture.LOAD.data,
            )
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
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((100..150).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            GDX_GLOBAL_isLoadAssets = true
            gdxGame.activity.hideWebView()

            game.musicUtil.apply {
                coff  = 0.15f
                music = italian.apply { isLooping = true }
            }

            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(SelectBirdScreen::class.java.name)
            }
        }
    }


}