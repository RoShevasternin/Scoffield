package com.minimal.endless.races.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor
import com.minimal.endless.races.game.actors.AImage
import com.minimal.endless.races.game.box2d.AbstractBody
import com.minimal.endless.races.game.box2d.BodyId
import com.minimal.endless.races.game.utils.actor.setPosition
import com.minimal.endless.races.game.utils.advanced.AdvancedBox2dScreen
import com.minimal.endless.races.game.utils.advanced.AdvancedGroup
import com.minimal.endless.races.game.utils.toB2
import com.minimal.endless.races.game.utils.toUI

class BCar(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "car"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.KinematicBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = 0.7f
        friction    = 0.2f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.car)

    override var id = BodyId.CAR
    override val collisionList = mutableListOf(BodyId.ENEMY)

    private val particleEffect = ParticleEffectActor(ParticleEffect(screenBox2d.game.particleEffectUtil.CAR),false)

    override fun onCreate() {
        addEffect()
    }

    private fun addEffect() {
        screenBox2d.stageUI.addActor(particleEffect)
        particleEffect.start()
    }

    override fun render(deltaTime: Float) {
        super.render(deltaTime)
        particleEffect.setPosition(body!!.position.cpy().toUI.add(58f, 0f))
    }

}