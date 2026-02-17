package com.plinko.x10ballsss.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor
import com.plinko.x10ballsss.game.actors.AImage
import com.plinko.x10ballsss.game.box2d.AbstractBody
import com.plinko.x10ballsss.game.box2d.BodyId
import com.plinko.x10ballsss.game.utils.actor.setPosition
import com.plinko.x10ballsss.game.utils.advanced.AdvancedBox2dScreen
import com.plinko.x10ballsss.game.utils.advanced.AdvancedGroup
import com.plinko.x10ballsss.game.utils.toUI
import java.util.concurrent.atomic.AtomicBoolean

class BAsteroid(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
        restitution = 0.73f
        friction    = 0.28f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.asteroid.random())

    override val collisionList = mutableListOf(BodyId.ROCKET, BodyId.ASTEROID)

    // Field
    val atomicBoolean = AtomicBoolean(true)

    private val particleEffect = ParticleEffectActor(ParticleEffect(screenBox2d.game.particleEffectUtil.BOOM),false)

    fun addEffect() {
        screenBox2d.stageUI.addActor(particleEffect)
    }

    fun startEffect(position: Vector2) {
        particleEffect.apply {
            setPosition(body!!.position.toUI)
            start()
        }
    }

}