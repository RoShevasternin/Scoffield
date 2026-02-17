package com.piriamidone.twispinton.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.piriamidone.twispinton.game.LibGDXGame
import com.piriamidone.twispinton.game.utils.HEIGHT_UI
import com.piriamidone.twispinton.game.utils.ShapeDrawerUtil
import com.piriamidone.twispinton.game.utils.TIME_ANIM
import com.piriamidone.twispinton.game.utils.WIDTH_UI
import com.piriamidone.twispinton.game.utils.actor.animHide
import com.piriamidone.twispinton.game.utils.addProcessors
import com.piriamidone.twispinton.game.utils.disposeAll
import com.piriamidone.twispinton.game.utils.font.FontGenerator
import com.piriamidone.twispinton.game.utils.font.FontGenerator.Companion.FontPath
import com.piriamidone.twispinton.game.utils.gdxGame
import com.piriamidone.twispinton.game.utils.runGDX
import com.piriamidone.twispinton.util.cancelCoroutinesAll
import com.piriamidone.twispinton.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AdvancedScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), AdvancedInputProcessor {

    abstract val game: LibGDXGame

    private val viewportBack by lazy { FillViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()) }
    private val stageBack    by lazy { AdvancedStage(viewportBack) }

    val viewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val stageUI    by lazy { AdvancedStage(viewportUI) }

    val inputMultiplexer    = InputMultiplexer()
    val backBackgroundImage = Image()
    val uiBackgroundImage   = Image()
    val disposableSet       = mutableSetOf<Disposable>()
    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val drawerUtil by lazy { ShapeDrawerUtil(stageUI.batch) }

    val fontGenerator_MontrealBold = FontGenerator(FontPath.MontrealBold)

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height, true)
    }

    override fun show() {
        startScreen()

        stageBack.addAndFillActor(backBackgroundImage)
        stageUI.addAndFillActor(uiBackgroundImage)

        stageUI.addActorsOnStageUI()
        animShowScreen()

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, stageUI) }
//        Gdx.input.setCatchKey(Input.Keys.BACK, true)

        gdxGame.activity.webViewHelper.blockBack = {
            if (gdxGame.navigationManager.isBackStackEmpty()) gdxGame.navigationManager.exit()
            else stageUI.root.animHide(TIME_ANIM) { gdxGame.navigationManager.back() }
        }
    }

    private fun startScreen() {
        stageUI.root.setPosition(0f, -HEIGHT)
    }

    private fun animShowScreen() {
        stageUI.root.addAction(Actions.moveTo(0f, 0f, TIME_ANIM, Interpolation.fade))
    }

    fun animHideScreen(block: () -> Unit = {}) {
        stageUI.root.addAction(Actions.sequence(
            Actions.moveTo(0f, HEIGHT, TIME_ANIM, Interpolation.fade),
            Actions.run(block),
        ))
    }

    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()
        drawerUtil.update()
    }

    override fun dispose() {
        log("dispose AdvancedScreen: ${this::class.java.name.substringAfterLast('.')}")
        disposeAll(
            stageBack, stageUI, drawerUtil,
            fontGenerator_MontrealBold
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

//    override fun keyDown(keycode: Int): Boolean {
//        when(keycode) {
//            Input.Keys.BACK -> {
//                if (game.navigationManager.isBackStackEmpty()) game.navigationManager.exit()
//                else stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
//            }
//        }
//        return true
//    }

    open fun AdvancedStage.addActorsOnStageUI() {}


    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
    }

    fun setUIBackground(texture: TextureRegion) {
        uiBackgroundImage.drawable = TextureRegionDrawable(texture)
    }

    fun setBackgrounds(backRegion: TextureRegion, uiRegion: TextureRegion = backRegion) {
        setBackBackground(backRegion)
        //setUIBackground(uiRegion)
    }

}