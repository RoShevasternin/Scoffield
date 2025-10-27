package fortunetiger.com.tighrino.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import fortunetiger.com.tighrino.game.LibGDXGame
import fortunetiger.com.tighrino.game.actors.progress.IncasLoadingProgress
import fortunetiger.com.tighrino.game.manager.MusicManager
import fortunetiger.com.tighrino.game.manager.SoundManager
import fortunetiger.com.tighrino.game.manager.SpriteManager
import fortunetiger.com.tighrino.game.screens.common.IncasMenuScreen
import fortunetiger.com.tighrino.game.utils.actor.setBounds
import fortunetiger.com.tighrino.game.utils.advanced.AdvancedScreen
import fortunetiger.com.tighrino.game.utils.advanced.AdvancedStage
import fortunetiger.com.tighrino.game.utils.region
import fortunetiger.com.tighrino.game.utils.runGDX
import fortunetiger.com.tighrino.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import fortunetiger.com.tighrino.game.utils.Layout.Splash as LS

class IncasLoadingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val incasLoadingText       by lazy { Image(game.loadingAssets.aca_loading) }
    private val incasLoadingBackground by lazy { Image(game.loadingAssets.aca_background) }
    private val incasLoadingTiger      by lazy { Image(game.loadingAssets.aca_tiger) }
    private val incasLoadingProgress   by lazy { IncasLoadingProgress(this) }


    override fun show() {
//        stageUI.root.color.a = 0f
        loadSplashAssets()
        game.activity.lottie.hideLoader()
        setBackgrounds(game.loadingAssets.IncasBackground.region)
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
        addIncasLoadingText()
        addIncasLoadingBackground()
        addIncasLoadingTiger()
        addIncasLoadingProgress()

        isFinishAnim = true
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addIncasLoadingText() {
        addActor(incasLoadingText)
        incasLoadingText.setBounds(LS.incasLoadingText)
    }

    private fun AdvancedStage.addIncasLoadingBackground() {
        addActor(incasLoadingBackground)
        incasLoadingBackground.setBounds(LS.incasLoadingBackground)
    }

    private fun AdvancedStage.addIncasLoadingTiger() {
        addActor(incasLoadingTiger)
        incasLoadingTiger.setBounds(LS.incasLoadingTiger)
    }

    private fun AdvancedStage.addIncasLoadingProgress() {
        addActor(incasLoadingProgress)
        incasLoadingProgress.setBounds(LS.incasLoadingProgress)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.Loading.data)
            loadAtlas()
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.IncasBackground.data,
                SpriteManager.EnumTexture.aca_mask.data,
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
                    runGDX { incasLoadingProgress.setProgressPercent(progress.toFloat()) }
                    if (progress % 100 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((15..30).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            game.musicUtil.apply { music = FunnyBackground.apply { isLooping = true } }
            game.navigationManager.navigate(IncasMenuScreen::class.java.name)
        }
    }


}