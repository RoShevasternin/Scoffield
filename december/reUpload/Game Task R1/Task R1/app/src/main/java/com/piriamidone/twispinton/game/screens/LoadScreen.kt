package com.piriamidone.twispinton.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.piriamidone.twispinton.game.GDX_GLOBAL_isGame
import com.piriamidone.twispinton.game.GDX_GLOBAL_isLoadAssets
import com.piriamidone.twispinton.game.LibGDXGame
import com.piriamidone.twispinton.game.actors.progress.ALoading
import com.piriamidone.twispinton.game.manager.MusicManager
import com.piriamidone.twispinton.game.manager.SoundManager.*
import com.piriamidone.twispinton.game.manager.SpriteManager.*
import com.piriamidone.twispinton.game.utils.actor.setBounds
import com.piriamidone.twispinton.game.utils.advanced.AdvancedScreen
import com.piriamidone.twispinton.game.utils.advanced.AdvancedStage
import com.piriamidone.twispinton.game.utils.gdxGame
import com.piriamidone.twispinton.game.utils.region
import com.piriamidone.twispinton.game.utils.runGDX
import com.piriamidone.twispinton.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.piriamidone.twispinton.game.utils.Layout.Splash as LS

class LoadScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val pharaImg     by lazy { Image(game.loadAssets.phara) }
    private val progrImg     by lazy { Image(game.loadAssets.progress_back) }
    private val textImg      by lazy { Image(game.loadAssets.loading) }
    private val loading      by lazy { ALoading(this) }

    override fun show() {
        loadSplashAssets()
        setBackgrounds(game.loadAssets.background.region)
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
        addPhara()
        addProgress()
        addLoading()
        addText()

        isFinishAnim = true
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addPhara() {
        addActor(pharaImg)
        pharaImg.setBounds(LS.phara)
    }

    private fun AdvancedStage.addProgress() {
        addActor(progrImg)
        progrImg.setBounds(LS.progres)
    }

    private fun AdvancedStage.addLoading() {
        addActor(loading)
        loading.setBounds(LS.loading)
    }

    private fun AdvancedStage.addText() {
        addActor(textImg)
        textImg.setBounds(LS.loaring)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTextureList = mutableListOf(
                EnumTexture.background.data,
                EnumTexture.loader_mask.data,
                EnumTexture.loading.data,
                EnumTexture.phara.data,
                EnumTexture.progress.data,
                EnumTexture.progress_back.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTexture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTextureList = EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = EnumSound.entries.map { it.data }.toMutableList()
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
                    runGDX {
                        loading.setProgressPercent(progress.toFloat())
//                        progressLabel.setText("$progress%")
                    }
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


            game.musicUtil.apply { music = Eqipt_MUSIC.apply { isLooping = true } }
            animHideScreen { game.navigationManager.navigate(MenuScreen::class.java.name) }
        }
    }


}