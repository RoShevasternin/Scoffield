package com.obezana.playtocrypto.game.box2d

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.JointDef
import com.obezana.playtocrypto.game.utils.*
import com.obezana.playtocrypto.game.utils.advanced.AdvancedBox2dScreen

abstract class AbstractBodyGroup {
    abstract val screenBox2d: AdvancedBox2dScreen
    abstract val standartW  : Float

    private val destroyBodyList  = mutableListOf<AbstractBody>()
    private val destroyJointList = mutableListOf<AbstractJoint<out Joint>>()

    protected val standardizer = SizeStandardizer()

    val position = Vector2()

    open fun create(position: Vector2, size: Vector2) {
        this.position.set(position)
        standardizer.standardize(standartW, size.x)
    }

    open fun destroy(time: Long) {
        destroyBodyList.destroyAll(time)
        destroyJointList.destroyAll(time)
    }

    fun createBody(body: AbstractBody, x: Float, y: Float, w: Float, h: Float) {
        createBody(body, Vector2(x, y), Vector2(w, h))
    }

    fun createBody(body: AbstractBody, pos: Vector2, size: Vector2) {
        standardizer.scope { body.create(position.cpy().add(pos.toStandart), size.toStandart) }
        destroyBodyList.add(body)
    }

    fun createJoint(joint: AbstractJoint<out Joint>, jointDef: JointDef) {
        joint.create(jointDef)
        destroyJointList.add(joint)
    }


    protected fun Vector2.subCenter(body: Body): Vector2 = standardizer.scope {
        this@subCenter.toStandart.toB2.sub((body.userData as AbstractBody).center)
    }

}