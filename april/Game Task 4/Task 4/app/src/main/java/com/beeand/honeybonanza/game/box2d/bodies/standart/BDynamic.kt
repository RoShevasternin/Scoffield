package com.beeand.honeybonanza.game.box2d.bodies.standart

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.beeand.honeybonanza.game.actors.AImage
import com.beeand.honeybonanza.game.box2d.AbstractBody
import com.beeand.honeybonanza.game.utils.advanced.AdvancedBox2dScreen
import com.beeand.honeybonanza.game.utils.advanced.AdvancedGroup

class BDynamic(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d)
}