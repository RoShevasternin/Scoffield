package com.obezana.playtocrypto.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obezana.playtocrypto.game.manager.NavigationManager
import com.obezana.playtocrypto.game.utils.HEIGHT_UI
import com.obezana.playtocrypto.game.utils.WIDTH_UI
import com.obezana.playtocrypto.game.utils.addProcessors
import com.obezana.playtocrypto.game.utils.disposeAll
import com.obezana.playtocrypto.util.cancelCoroutinesAll
import com.obezana.playtocrypto.util.gdxGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


abstract class AdvancedScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), AdvancedInputProcessor {

    open val name: String = javaClass.name

    private val viewportBack by lazy { FillViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()) }
    private val stageBack    by lazy { AdvancedStage(viewportBack) }

    val viewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val stageUI    by lazy { AdvancedStage(viewportUI) }

    val inputMultiplexer    = InputMultiplexer()
    val backBackgroundImage = Image()
    val uiBackgroundImage   = Image()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val mainGroup = AdvancedGroup()

   // val drawerUtil by lazy { ShapeDrawerUtil(stageUI.batch) }


    override fun show() {
        stageBack.addActor(backBackgroundImage)
        stageUI.apply {
            addActor(uiBackgroundImage)
            addActorsOnStageUI()

            addAndFillActor(mainGroup)
            mainGroup.addActorsOnGroup()
        }

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, stageUI) }
        //Gdx.input.setCatchKey(Input.Keys.BACK, true)

        gdxGame.activity.webViewHelper.blockBack = {
            if (NavigationManager.backStack.isEmpty()) NavigationManager.exit()
            else NavigationManager.back()
        }
    }

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height, true)
    }

    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()
    //    drawerUtil.update()
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        disposeAll(stageBack, stageUI, /*drawerUtil*/)
        inputMultiplexer.clear()
    }

//    override fun keyDown(keycode: Int): Boolean {
//        if (keycode == Input.Keys.BACK) NavigationManager.back()
//        return super.keyDown(keycode)
//    }

    open fun AdvancedStage.addActorsOnStageUI() {}
    open fun AdvancedGroup.addActorsOnGroup() {}



    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.apply {
            drawable = TextureRegionDrawable(region)
            setSize(viewportBack.worldWidth, viewportBack.worldHeight)
        }
    }

    fun setUIBackground(texture: TextureRegion) {
        uiBackgroundImage.apply {
            drawable = TextureRegionDrawable(texture)
            setSize(WIDTH, HEIGHT)
        }
    }

    fun setBackgrounds(backRegion: TextureRegion, uiRegion: TextureRegion = backRegion) {
        setBackBackground(backRegion)
        setUIBackground(uiRegion)
    }

}