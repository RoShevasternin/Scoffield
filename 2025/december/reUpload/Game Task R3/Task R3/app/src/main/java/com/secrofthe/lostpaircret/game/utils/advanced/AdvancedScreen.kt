package com.secrofthe.lostpaircret.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.secrofthe.lostpaircret.game.LibGDXGame
import com.secrofthe.lostpaircret.game.utils.HEIGHT_UI
import com.secrofthe.lostpaircret.game.utils.ShapeDrawerUtil
import com.secrofthe.lostpaircret.game.utils.TIME_ANIM_T
import com.secrofthe.lostpaircret.game.utils.WIDTH_UI
import com.secrofthe.lostpaircret.game.utils.actor.animHide
import com.secrofthe.lostpaircret.game.utils.addProcessors
import com.secrofthe.lostpaircret.game.utils.disposeAll
import com.secrofthe.lostpaircret.game.utils.font.FontGenerator
import com.secrofthe.lostpaircret.game.utils.font.FontGenerator.Companion.FontPath
import com.secrofthe.lostpaircret.game.utils.gdxGame
import com.secrofthe.lostpaircret.game.utils.runGDX
import com.secrofthe.lostpaircret.util.cancelCoroutinesAll
import com.secrofthe.lostpaircret.util.log

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

    val fontGenerator_InriaSans_Bold = FontGenerator(FontPath.InriaSans_Bold)

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height, true)
    }

    override fun show() {
//        game.activity.webViewFragment.backBlock = { runGDX { stageUI.root.animHide(TIME_ANIM_T) { game.navigationManager.back() }}}

        stageBack.addAndFillActor(backBackgroundImage)
        stageUI.addAndFillActor(uiBackgroundImage)

        stageUI.addActorsOnStageUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, stageUI) }
//        Gdx.input.setCatchKey(Input.Keys.BACK, true)

        gdxGame.activity.webViewHelper.blockBack = {
            if (gdxGame.navigationManager.isBackStackEmpty()) gdxGame.navigationManager.exit()
            else stageUI.root.animHide(TIME_ANIM_T) { gdxGame.navigationManager.back() }
        }

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
            fontGenerator_InriaSans_Bold,
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }
//
//    override fun keyDown(keycode: Int): Boolean {
//        when(keycode) {
//            Input.Keys.BACK -> {
//                if (game.navigationManager.isBackStackEmpty()) game.navigationManager.exit() else game.navigationManager.back()
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
        setUIBackground(uiRegion)
    }

}