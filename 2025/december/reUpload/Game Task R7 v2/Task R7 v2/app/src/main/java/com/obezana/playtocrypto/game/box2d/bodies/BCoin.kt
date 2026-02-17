package com.obezana.playtocrypto.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.obezana.playtocrypto.game.actors.image.AImage
import com.obezana.playtocrypto.game.box2d.AbstractBody
import com.obezana.playtocrypto.game.manager.SpriteManager
import com.obezana.playtocrypto.game.utils.advanced.AdvancedBox2dScreen
import com.obezana.playtocrypto.game.utils.advanced.AdvancedGroup

class BCoin(override val screenBox2d: AdvancedBox2dScreen, indexRegion: Int): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }
    override var actor: AdvancedGroup? = AImage(SpriteManager.ListRegion.DOBRI.regionList[indexRegion])
}