package com.plinko.ballwinx100.game.screens.game

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.scenes.scene2d.Actor

class BonusBasketActor (
    private val body: Body,
    private val image: TextureRegion,
    private val ppm: Float
    ) : Actor() {

        override fun act(delta: Float) {
            super.act(delta)
            val pos = body.position
            setPosition(pos.x * ppm - width / 2f, pos.y * ppm - height / 2f)
        }

        override fun draw(batch: Batch, parentAlpha: Float) {
            batch.draw(image, x, y, width, height)
        }
    }
