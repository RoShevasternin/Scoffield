package com.hexofun.rundeo.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.hexofun.rundeo.game.actors.AHexagon
import com.hexofun.rundeo.game.actors.image.AImage
import com.hexofun.rundeo.game.box2d.AbstractBody
import com.hexofun.rundeo.game.box2d.BodyId
import com.hexofun.rundeo.game.utils.advanced.AdvancedBox2dScreen
import com.hexofun.rundeo.game.utils.advanced.AdvancedGroup

class BStar(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "star"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.star)

    override var id = BodyId.FunWorld.STAR
    override val collisionList = mutableListOf(BodyId.FunWorld.HEXAGON)
}