package com.beeand.honeybonanza.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.beeand.honeybonanza.game.box2d.AbstractBody
import com.beeand.honeybonanza.game.utils.advanced.AdvancedBox2dScreen

class BHorizontal(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "h"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        friction = 0f
    }

}