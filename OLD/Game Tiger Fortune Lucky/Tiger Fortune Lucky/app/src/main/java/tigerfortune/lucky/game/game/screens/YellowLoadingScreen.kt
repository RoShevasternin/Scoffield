package tigerfortune.lucky.game.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tigerfortune.lucky.game.game.LibGDXGame
import tigerfortune.lucky.game.game.actors.progress.YellowProgress
import tigerfortune.lucky.game.game.manager.MusicManager
import tigerfortune.lucky.game.game.manager.SoundManager
import tigerfortune.lucky.game.game.manager.SpriteManager
import tigerfortune.lucky.game.game.utils.TIME_ANIM
import tigerfortune.lucky.game.game.utils.actor.animShow
import tigerfortune.lucky.game.game.utils.actor.setBounds
import tigerfortune.lucky.game.game.utils.advanced.AdvancedScreen
import tigerfortune.lucky.game.game.utils.advanced.AdvancedStage
import tigerfortune.lucky.game.game.utils.region
import tigerfortune.lucky.game.game.utils.runGDX
import tigerfortune.lucky.game.util.log
import tigerfortune.lucky.game.game.utils.Layout.Splash as LS

class YellowLoadingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val yellowProgress by lazy { YellowProgress(this) }


    override fun show() {
        stageUI.root.color.a = 0f
        loadSplashAssets()
        game.activity.lottie.hideLoader()
        setUIBackground(game.loadingAssets.YellowLoading.region)
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
        addYellowLoading()
        addYellowLoadingText()

        stageUI.root.animShow(TIME_ANIM) {
            isFinishAnim = true
        }

    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addYellowLoading() {
        addActor(yellowProgress)
        yellowProgress.setBounds(LS.progress)
    }

    private fun AdvancedStage.addYellowLoadingText() {
        val img = Image(game.loadingAssets.startLoading)
        addActor(img)
        img.setBounds(LS.loading)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADING.data)
            loadAtlas()
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.YellowLoading.data,
                SpriteManager.EnumTexture.YellowMasakaj.data,
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
                    runGDX { yellowProgress.setProgressPercent(progress.toFloat()) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((15..30).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            game.musicUtil.apply { music = MUZLO.apply { isLooping = true } }
            game.navigationManager.navigate(YellowMenuingScreen::class.java.name)
        }
    }


}