package com.obezana.playtocrypto.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.obezana.playtocrypto.MainActivity
import com.obezana.playtocrypto.game.GDX_GLOBAL_isGame
import com.obezana.playtocrypto.game.game
import com.obezana.playtocrypto.game.manager.FontTTFManager
import com.obezana.playtocrypto.game.manager.NavigationManager
import com.obezana.playtocrypto.game.manager.SpriteManager
import com.obezana.playtocrypto.game.utils.actor.animHide
import com.obezana.playtocrypto.game.utils.advanced.AdvancedGroup
import com.obezana.playtocrypto.game.utils.advanced.AdvancedScreen
import com.obezana.playtocrypto.util.gdxGame
import com.obezana.playtocrypto.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ZagruzkaScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

//    private val progressLabel by lazy { Label("", Label.LabelStyle(FontTTFManager.Inter.ExtraBold.font_100.font, GameColor.textGreen)) }
    private val dukak by lazy { Image(SpriteManager.SplashRegion.XUESOS.region) }
    private val lober by lazy { Image(SpriteManager.SplashRegion.LORAN.region) }

    override fun show() {
        loadSplashAssets()
        //setUIBackground(SpriteManager.SplashRegion.ZAGRUZGA_BACKGROUND.region)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }


    override fun AdvancedGroup.addActorsOnGroup() {
        addDurakaIIIloader()
        dukak.addAction(Actions.sequence(
            Actions.scaleTo(1f, 1f, 0.3f),
            Actions.forever(Actions.sequence(
                Actions.scaleTo(0.8f, 0.8f, 0.3f),
                Actions.scaleTo(1f, 1f, 0.3f),
            ))
        ))
        lober.addAction(Actions.sequence(
            Actions.scaleTo(1f, 1f, 0.3f),
            Actions.forever(Actions.rotateBy(-360f, 0.5f))
        ))
        isFinishAnim = true
    }


    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedGroup.addDurakaIIIloader() {
        addActors(/*dukak*/ lober)
        dukak.apply {
            setBounds(320f, 195f, 990f, 549f)
            setOrigin(Align.center)
            addAction(Actions.scaleTo(0f, 0f))
        }
        lober.apply {
            setBounds(714f, 336f, 127f, 127f)
            setOrigin(Align.center)
            addAction(Actions.scaleTo(0f, 0f))
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(SpriteManager) {
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.LORAN,
                SpriteManager.EnumTexture.XUESOS,
                SpriteManager.EnumTexture.ZAGRUZGA_BACKGROUND,
            )
            loadTexture(game.assetManager)
        }
        game.assetManager.finishLoading()

        SpriteManager.initTexture(game.assetManager)
    }

    private fun loadAssets() {
        with(SpriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.values().toMutableList()
            loadAtlas(game.assetManager)
            loadableTextureList = SpriteManager.EnumTexture.values().toMutableList()
            loadTexture(game.assetManager)
        }
        with(FontTTFManager) {
            loadableFontList = (FontTTFManager.Regularka.values).toMutableList()
            load(game.assetManager)
        }
    }

    private fun initAssets() {
        SpriteManager.initAtlas(game.assetManager)
        SpriteManager.initTexture(game.assetManager)
        FontTTFManager.init(game.assetManager)
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(17)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    //runGDX { progressLabel.setText("$progress%") }
                    if (progress % 100 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true
                    //delay((4..7).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            gdxGame.activity.hideWebView()

            stageUI.root.animHide(0.5f) { NavigationManager.navigate(PickACointScreen()) }
        }
    }


}