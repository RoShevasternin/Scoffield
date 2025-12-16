package com.dliw.drib.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.dliw.drib.game.box2d.AbstractBodyGroup
import com.dliw.drib.game.box2d.AbstractJoint
import com.dliw.drib.game.box2d.BodyId
import com.dliw.drib.game.box2d.bodies.BBeam
import com.dliw.drib.game.box2d.bodies.standart.BDynamic
import com.dliw.drib.game.utils.advanced.AdvancedBox2dScreen
import com.dliw.drib.game.utils.toB2

class BGBeam(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = 366f

    // Body
    val bBeamTop    = BBeam(screenBox2d)
    val bBeamBottom = BBeam(screenBox2d)
    val bCircle     = BDynamic(screenBox2d)

    // Joint
    private val jDistance = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jWeld     = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        createB_Beam()
        createB_Dynamic()

        createJ_Weld()
        createJ_Distance()
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Beam() {
        createBody(bBeamTop, 0f, 1020f, 366f, 700f)
        createBody(bBeamBottom, 0f, 0f, 366f, 700f)
    }

    private fun createB_Dynamic() {
        bCircle.apply {
            id = BodyId.Coyntry.WIN
            collisionList.add(BodyId.Coyntry.BIRD)
            fixtureDef.isSensor = true
        }
        createBody(bCircle, 48f, 725f, 271f, 271f)
    }

    // ---------------------------------------------------
    // create Joint
    // ---------------------------------------------------

    private fun createJ_Weld() {
        createJoint(jWeld, WeldJointDef().apply {
            bodyA = bBeamTop.body
            bodyB = bCircle.body

            localAnchorA.set(Vector2(183f, -159f).subCenter(bodyA))
            localAnchorB.set(Vector2(135f, 135f).subCenter(bodyB))
        })
    }

    private fun createJ_Distance() {
        createJoint(jDistance, DistanceJointDef().apply {
            bodyA = bBeamTop.body
            bodyB = bBeamBottom.body

            localAnchorA.set(Vector2(183f, 0f).subCenter(bodyA))
            localAnchorB.set(Vector2(183f, 700f).subCenter(bodyB))

            length      = 320f.toB2
            frequencyHz = 2f
        })
    }

}