package com.fluxton.ballerina.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.fluxton.ballerina.game.GDX_GLOBAL_isGame
import com.fluxton.ballerina.game.GDX_GLOBAL_isLoadAssets
import com.fluxton.ballerina.game.LibGDXGame
import com.fluxton.ballerina.game.manager.MusicManager
import com.fluxton.ballerina.game.manager.SoundManager
import com.fluxton.ballerina.game.manager.SpriteManager
import com.fluxton.ballerina.game.utils.TIME_ANIM
import com.fluxton.ballerina.game.utils.actor.animHide
import com.fluxton.ballerina.game.utils.advanced.AdvancedScreen
import com.fluxton.ballerina.game.utils.advanced.AdvancedStage
import com.fluxton.ballerina.game.utils.region
import com.fluxton.ballerina.gdxGame
import com.fluxton.ballerina.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PinkLoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        //setBackBackground(game.startAssets._3.region)
        super.show()

        stageUI.addActor(Image(gdxGame.startAssets.leo).also {
            it.setBounds(323f, 743f, 434f, 434f)
            it.setOrigin(Align.center)
            it.addAction(Actions.forever(Actions.rotateBy(-360f, 0.95f)))
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
                SpriteManager.EnumTexture._3.data,
                SpriteManager.EnumTexture.leo.data,
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

                    //delay((10..15).shuffled().first().toLong())
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
                coff  = 0.5f
                music = RELAXING.apply { isLooping = true }
            }

            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(PinkMenuScreen::class.java.name)
            }
        }
    }


}