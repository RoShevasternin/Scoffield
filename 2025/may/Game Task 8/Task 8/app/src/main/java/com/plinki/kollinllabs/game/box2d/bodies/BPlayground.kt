package com.plinki.kollinllabs.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.plinki.kollinllabs.game.actors.image.AImage
import com.plinki.kollinllabs.game.box2d.AbstractBody
import com.plinki.kollinllabs.game.utils.advanced.AdvancedBox2dScreen
import com.plinki.kollinllabs.game.utils.advanced.AdvancedGroup
import com.plinki.kollinllabs.game.utils.gdxGame

class BPlayground(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "playground"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = 0.3f
        friction    = 0.3f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.GAME)

}