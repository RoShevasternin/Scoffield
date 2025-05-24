package com.bonanza.sweetcards.game.util.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.bonanza.sweetcards.game.game
import com.bonanza.sweetcards.game.manager.NavigationManager
import com.bonanza.sweetcards.game.util.addProcessors
import com.bonanza.sweetcards.game.util.disposeAll
import com.bonanza.sweetcards.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


abstract class AdvancedScreen(val WIDTH: Float = 1200f, val HEIGHT: Float = 676f) : ScreenAdapter(), AdvancedInputProcessor {

    private val viewportBack by lazy { ScreenViewport() }
    private val stageBack    by lazy { AdvancedStage(viewportBack) }

    val viewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val stageUI    by lazy { AdvancedStage(viewportUI) }

    val inputMultiplexer    = InputMultiplexer()
    val backBackgroundImage = Image()
    val uiBackgroundImage   = Image()

    val coroutine = CoroutineScope(Dispatchers.Default)



    override fun show() {
        stageBack.addAndFillActor(backBackgroundImage)
        stageUI.apply {
            addActor(uiBackgroundImage)
            addActorsOnStageUI()
        }

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, stageUI) }
//        Gdx.input.setCatchKey(Input.Keys.BACK, true)

        game.activity.blockBack = {
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
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        disposeAll(stageBack, stageUI)
        inputMultiplexer.clear()
    }

//    override fun keyDown(keycode: Int): Boolean {
//        if (keycode == Input.Keys.BACK) NavigationManager.back()
//        return super.keyDown(keycode)
//    }

    open fun AdvancedStage.addActorsOnStageUI() {}



    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
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