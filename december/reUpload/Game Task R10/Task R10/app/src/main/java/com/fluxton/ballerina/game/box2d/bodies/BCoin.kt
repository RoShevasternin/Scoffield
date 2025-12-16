package com.fluxton.ballerina.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fluxton.ballerina.game.actors.image.AImage
import com.fluxton.ballerina.game.box2d.AbstractBody
import com.fluxton.ballerina.game.box2d.BodyId
import com.fluxton.ballerina.game.utils.advanced.AdvancedBox2dScreen
import com.fluxton.ballerina.game.utils.advanced.AdvancedGroup

class BCoin(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply { isSensor = true }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.coin)

    override var id = BodyId.COIN
    override val collisionList = mutableListOf(BodyId.BALL)
}