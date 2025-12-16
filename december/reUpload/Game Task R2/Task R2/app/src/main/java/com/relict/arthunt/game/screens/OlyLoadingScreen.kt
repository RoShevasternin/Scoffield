package com.relict.arthunt.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relict.arthunt.game.GDX_GLOBAL_isGame
import com.relict.arthunt.game.GDX_GLOBAL_isLoadAssets
import com.relict.arthunt.game.LibGDXGame
import com.relict.arthunt.game.actors.progress.AProgressBar
import com.relict.arthunt.game.manager.MusicManager
import com.relict.arthunt.game.manager.SoundManager
import com.relict.arthunt.game.manager.SpriteManager
import com.relict.arthunt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.relict.arthunt.game.utils.actor.animHide
import com.relict.arthunt.game.utils.advanced.AdvancedScreen
import com.relict.arthunt.game.utils.advanced.AdvancedStage
import com.relict.arthunt.game.utils.gdxGame
import com.relict.arthunt.game.utils.region
import com.relict.arthunt.game.utils.runGDX
import com.relict.arthunt.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OlyLoadingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val progressBar by lazy { AProgressBar(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.loadingAssets.BACKICH.region)
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
        addProgress()

        val loading = Image(game.loadingAssets.lodrink)
        addActor(loading)
        loading.setBounds(406f, 1397f, 192f, 77f)

        val oly = Image(game.loadingAssets.bik_bak)
        //addActor(oly)
        oly.setBounds(20f, -8f, 1040f, 991f)

        isFinishAnim = true
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addProgress() {
        addActor(progressBar)
        progressBar.setBounds(63f, 1377f, 954f, 110f)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList   = mutableListOf(SpriteManager.EnumAtlas.LOADING.data)
            loadAtlas()
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.BACKICH.data,
                SpriteManager.EnumTexture.MIKELANDJELO.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initAtlasAndTexture()
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
                    runGDX {
                        progressBar.setProgressPercent(progress.toFloat())
//                        progressLbl.setText("$progress%")
                    }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((15..30).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            GDX_GLOBAL_isLoadAssets = true
            gdxGame.activity.hideWebView()

            game.musicUtil.apply { music = temple.apply { isLooping = true } }

            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.navigationManager.navigate(OlyMenuScreen::class.java.name)
            }
        }
    }


}