package com.plinkray.ioosudmarine.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.plinkray.ioosudmarine.game.actors.image.AImage
import com.plinkray.ioosudmarine.game.box2d.AbstractBody
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedBox2dScreen
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedGroup

class BFinishSensor(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        isSensor = true
    }

}