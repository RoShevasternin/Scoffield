package lucky.jogodobicho.fan.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Label
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.game.LibGDXGame
import lucky.jogodobicho.fan.game.actors.background.ABackgroundSplash
import lucky.jogodobicho.fan.game.actors.progress.AFlatter
import lucky.jogodobicho.fan.game.manager.MusicManager
import lucky.jogodobicho.fan.game.manager.SoundManager
import lucky.jogodobicho.fan.game.manager.SpriteManager
import lucky.jogodobicho.fan.game.utils.GameColor
import lucky.jogodobicho.fan.game.utils.Time_ANIMATION
import lucky.jogodobicho.fan.game.utils.actor.animShow
import lucky.jogodobicho.fan.game.utils.actor.setBounds
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedStage
import lucky.jogodobicho.fan.game.utils.font.FontParameter
import lucky.jogodobicho.fan.game.utils.region
import lucky.jogodobicho.fan.game.utils.runGDX
import lucky.jogodobicho.fan.util.log
import lucky.jogodobicho.fan.game.utils.Layout.Splash as LS

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val parameter = FontParameter()

    private val flatterLabel by lazy { Label("", Label.LabelStyle(fontRegular.generateFont(parameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"LOADING%.").setSize(32)), GameColor.jelti)).apply { color.a = 0f } }
    private val flatter      by lazy { AFlatter(this).apply { color.a = 0f } }


    override fun show() {
        loadSplashAssets()
        game.activity.lottie.hideLoader()
        setBackgrounds(game.splashAssets.MAIN_BACKGROUND.region)
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
        coroutine?.launch {
            delay(500)
            runGDX {
                addAndFillActor(ABackgroundSplash(this@LoaderScreen))
                addFlatter()
                addFlatterLabel()
            }
            delay(500)
            isFinishAnim = true
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addFlatter() {
        addActor(flatter)
        flatter.apply {
            setBounds(LS.flatter)
            animShow(Time_ANIMATION)
        }
    }

    private fun AdvancedStage.addFlatterLabel() {
        addActor(flatterLabel)
        flatterLabel.apply {
            setBounds(LS.flatterLbl)
            animShow(Time_ANIMATION)
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.SPLASH.data)
            loadAtlas()
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.MAIN_BACKGROUND.data,
                SpriteManager.EnumTexture.IRON_MAN.data,
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
                    runGDX {
                        flatter.setProgressPercent(progress.toFloat())
                        flatterLabel.setText("LOADING $progress...")
                    }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((20..50).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            game.musicUtil.apply { music = pianos_by_jtwayne.apply { isLooping = true } }
            animHideScreen { game.navigationManager.navigate(A11Screen::class.java.name) }
        }
    }


}