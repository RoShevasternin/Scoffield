package com.plinko.ballwinx100.game.screens.game

import android.util.Log
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image

class BallActor(
    private val body: Body,
    private val image: Image,
    private val ppm: Float,
    private val onOut: () -> Unit
) : Actor() {

    private val TAG = "BallActor"

    private var isOutTriggered = false

    override fun setVisible(visible: Boolean) {
        image.isVisible = visible
    }

    init {
        Log.d(TAG, "init: isTouchable: $isTouchable")
        setSize(image.width, image.height)

        body.isAwake = true
        body.applyForceToCenter(0f, -10f, true)
    }

    override fun act(delta: Float) {
        super.act(delta)
        val pos = body.position
        val visualX = pos.x * ppm - width / 2f
        val visualY = pos.y * ppm - height / 2f
        image.setPosition(
            visualX,
            visualY
        )
        setBounds(visualX, visualY, width, height)

        val screenWidth = stage.viewport.worldWidth
        val screenHeight = stage.viewport.worldHeight
        val isOut = pos.x < 0 || pos.x > screenWidth / ppm || pos.y < -0.5f || pos.y > screenHeight / ppm

        if (isOut && !isOutTriggered) {
            // Remove from stage and Box2D world
            isOutTriggered = true
            onOut()
        }
    }

    override fun isVisible(): Boolean {
        return image.isVisible
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        image.draw(batch, parentAlpha)
    }
}
