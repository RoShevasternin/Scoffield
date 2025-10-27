package com.plinko.ballwinx100.game.screens.game

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image

class PinActor(
    private val body: Body,
    private val image: Image,
    private val ppm: Float
) : Actor() {

    init {
        /*width = image.width.toFloat()
        height = image.height.toFloat()
        originX = width / 2f
        originY = height / 2f*/
        setSize(image.width, image.height)
        setSize(
            image.width.toFloat(),
            image.height.toFloat()
        )
        // Optional
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
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        image.draw(batch, parentAlpha)
    }
}