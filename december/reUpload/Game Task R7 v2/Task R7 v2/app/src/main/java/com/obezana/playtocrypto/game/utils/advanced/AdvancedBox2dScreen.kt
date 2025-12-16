package com.obezana.playtocrypto.game.utils.advanced

import com.badlogic.gdx.utils.viewport.FitViewport
import com.obezana.playtocrypto.game.box2d.WorldUtil
import com.obezana.playtocrypto.game.utils.*

abstract class AdvancedBox2dScreen(
    val worldUtil: WorldUtil,
    val uiW  : Float = WIDTH_UI,
    val uiH  : Float = HEIGHT_UI,
    val boxW : Float = WIDTH_BOX2D,
    val boxH : Float = HEIGHT_BOX2D,
): AdvancedScreen(uiW, uiH) {

    private val viewportBox2d by lazy { FitViewport(boxW, boxH) }

    var isPause = false

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewportBox2d.update(width, height, true)
    }

    override fun render(delta: Float) {
        super.render(delta)
        if (isPause.not()) {
            worldUtil.update(delta)
            worldUtil.debug(viewportBox2d.camera.combined)
        }
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        super.dispose()
        disposeAll(worldUtil)
    }

}