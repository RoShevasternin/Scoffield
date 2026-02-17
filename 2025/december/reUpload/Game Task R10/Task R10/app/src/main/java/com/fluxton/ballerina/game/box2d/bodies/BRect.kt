package com.fluxton.ballerina.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fluxton.ballerina.game.actors.image.AImage
import com.fluxton.ballerina.game.box2d.AbstractBody
import com.fluxton.ballerina.game.box2d.BodyId
import com.fluxton.ballerina.game.utils.advanced.AdvancedBox2dScreen
import com.fluxton.ballerina.game.utils.advanced.AdvancedGroup

class BRect(override val screenBox2d: AdvancedBox2dScreen, ninePatch: NinePatch): AbstractBody() {
    override val name       = "rect"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = 0.3f
        friction    = 0.3f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, ninePatch)

    override var id = BodyId.BORDERS
    override val collisionList = mutableListOf(BodyId.BALL)
}