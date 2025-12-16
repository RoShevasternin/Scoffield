package com.dliw.drib.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.dliw.drib.game.actors.image.AImageAnim
import com.dliw.drib.game.box2d.AbstractBody
import com.dliw.drib.game.box2d.BodyId
import com.dliw.drib.game.screens.SelectBirdScreen
import com.dliw.drib.game.utils.advanced.AdvancedBox2dScreen
import com.dliw.drib.game.utils.advanced.AdvancedGroup

class BBird(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        fixedRotation = true
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }

    override var actor: AdvancedGroup? = AImageAnim(screenBox2d, 0.3f, getImgList(), Animation.PlayMode.LOOP)

    private fun getImgList(): List<TextureRegion> = when (SelectBirdScreen.indexBird) {
        0    -> screenBox2d.game.allAssets.a_bird
        1    -> screenBox2d.game.allAssets.b_bird
        2    -> screenBox2d.game.allAssets.c_bird
        else -> screenBox2d.game.allAssets.a_bird
    }

    override var id            = BodyId.Coyntry.BIRD
    override val collisionList = mutableListOf(BodyId.BORDERS, BodyId.Coyntry.WIN)

}