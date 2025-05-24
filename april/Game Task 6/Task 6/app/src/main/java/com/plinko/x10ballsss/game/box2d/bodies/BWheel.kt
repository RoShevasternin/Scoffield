package com.plinko.x10ballsss.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.plinko.x10ballsss.game.actors.AImage
import com.plinko.x10ballsss.game.box2d.AbstractBody
import com.plinko.x10ballsss.game.box2d.BodyId
import com.plinko.x10ballsss.game.utils.advanced.AdvancedBox2dScreen
import com.plinko.x10ballsss.game.utils.advanced.AdvancedGroup

class BWheel(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density  = 1f
        isSensor = true
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.wheel)

    override var id = BodyId.WHEEL

}