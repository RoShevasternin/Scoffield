package com.plinki.kollinllabs.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.plinki.kollinllabs.game.actors.image.AImage
import com.plinki.kollinllabs.game.box2d.AbstractBody
import com.plinki.kollinllabs.game.utils.advanced.AdvancedBox2dScreen
import com.plinki.kollinllabs.game.utils.advanced.AdvancedGroup
import com.plinki.kollinllabs.game.utils.gdxGame
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

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

    override var actor: AdvancedGroup? = AImage(screenBox2d,
        if (Random.nextBoolean()) gdxGame.assetsAll.blu else gdxGame.assetsAll.red
    )

    var isFirst = AtomicBoolean(true)

}