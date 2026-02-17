package com.plinko.aviator.slot.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.plinko.aviator.slot.game.actors.image.AImage
import com.plinko.aviator.slot.game.box2d.AbstractBody
import com.plinko.aviator.slot.game.utils.advanced.AdvancedBox2dScreen
import com.plinko.aviator.slot.game.utils.advanced.AdvancedGroup
import java.util.concurrent.atomic.AtomicBoolean

class BBall(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 10f
        restitution = 0.1f
        friction    = 0.1f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.ball)

    var isFirst = AtomicBoolean(true)

}