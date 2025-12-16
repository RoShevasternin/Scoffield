package com.triplees.linees.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.triplees.linees.game.GDX_GLOBAL_isGame
import com.triplees.linees.game.GDX_GLOBAL_isLoadAssets
import com.triplees.linees.game.LibGDXGame
import com.triplees.linees.game.manager.MusicManager
import com.triplees.linees.game.manager.SoundManager
import com.triplees.linees.game.manager.SpriteManager
import com.triplees.linees.game.utils.advanced.AdvancedScreen
import com.triplees.linees.game.utils.gdxGame
import com.triplees.linees.game.utils.region
import com.triplees.linees.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        setBackgrounds(game.loaderAssets.piramida.region)

        super.show()
        stageUI.addActor(Image(gdxGame.loaderAssets.loa).also {
            it.setBounds(429f, 849f, 221f, 221f)
            it.setOrigin(Align.center)
            it.addAction(Actions.forever(Actions.rotateBy(360f, 1.35f)))
        })

        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.piramida.data,
                SpriteManager.EnumTexture.loa.data,
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
                    if (progress % 100 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((70..120).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            GDX_GLOBAL_isLoadAssets = true
            gdxGame.activity.hideWebView()


            game.musicUtil.apply { music = BELLY.apply {
                coff      = 0.33f
                isLooping = true
            } }
            game.navigationManager.navigate(MenuScreen::class.java.name)
        }
    }


}