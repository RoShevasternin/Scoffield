package com.plinko.aviator.slot.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.plinko.aviator.slot.game.actors.image.AImage
import com.plinko.aviator.slot.game.box2d.AbstractBody
import com.plinko.aviator.slot.game.utils.advanced.AdvancedBox2dScreen
import com.plinko.aviator.slot.game.utils.advanced.AdvancedGroup

class BCircle(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = (10..50).random() / 100f
    }

}