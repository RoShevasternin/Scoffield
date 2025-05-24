package com.plinko.x10ballsss.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.*
import com.plinko.x10ballsss.game.LibGDXGame
import com.plinko.x10ballsss.game.box2d.AbstractBody
import com.plinko.x10ballsss.game.box2d.AbstractJoint
import com.plinko.x10ballsss.game.box2d.BodyId
import com.plinko.x10ballsss.game.box2d.bodies.BAsteroid
import com.plinko.x10ballsss.game.box2d.bodies.BRocket
import com.plinko.x10ballsss.game.box2d.bodies.BStatic
import com.plinko.x10ballsss.game.box2d.bodies.BWheel
import com.plinko.x10ballsss.game.utils.TIME_ANIM
import com.plinko.x10ballsss.game.utils.actor.animHide
import com.plinko.x10ballsss.game.utils.actor.animShow
import com.plinko.x10ballsss.game.utils.advanced.AdvancedStage
import com.plinko.x10ballsss.game.utils.advanced.AdvancedUserScreen
import com.plinko.x10ballsss.game.utils.advanced.isBackgroundMove
import com.plinko.x10ballsss.game.utils.runGDX
import com.plinko.x10ballsss.game.utils.toB2
import com.plinko.x10ballsss.game.utils.toUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class StarGameScreen(override val game: LibGDXGame): AdvancedUserScreen() {

    // Body
    private val bRocket = BRocket(this)
    private val bWheel  = BWheel(this)
    private val bStatic = BStatic(this)

    private val bListAsteroid = List(5) { BAsteroid(this) }

    // Joint
    private val jRevolute  = AbstractJoint<RevoluteJoint, RevoluteJointDef>(this)
    private val jPrismatic = AbstractJoint<PrismaticJoint, PrismaticJointDef>(this)
    private val jGear      = AbstractJoint<GearJoint, GearJointDef>(this)

    // Field
    private val asteroidFlow  = MutableSharedFlow<BAsteroid>(replay = 5)

    private val getRandomAsteroidX: Float get() = (0..930).random().toFloat().toB2

    override fun show() {
        isBackgroundMove = true
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Asteroid()
        createB_Rocket()
        createB_Wheel()
        createB_Static()

        createJ_Revolute()
        createJ_Prismatic()
        createJ_Gear()

        bListAsteroid.onEach { it.addEffect() }
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Asteroid() {
        bListAsteroid.onEach { bAstr ->
//            bA.addEffect()

            bAstr.renderBlockArray.add(AbstractBody.RenderBlock {
                bAstr.body?.let {
                    if (it.position.y <= -2f) {
                        if (bAstr.atomicBoolean.getAndSet(false)) asteroidFlow.tryEmit(bAstr)
                    }
                }
            })

            bAstr.id = BodyId.ASTEROID
            bAstr.setNoneId()

            bAstr.bodyDef.gravityScale = 0f
            bAstr.create(-200f, HEIGHT + 150f, 150f, 150f)

            asteroidFlow.tryEmit(bAstr)
        }

        coroutine?.launch {
            asteroidFlow.collect { bAstr ->
                runGDX {
                    bAstr.setNoneId()
                    bAstr.body?.apply {
                        setTransform(Vector2(-200f, HEIGHT + 150f).toB2, 0f)

                        setLinearVelocity(0f, 0f)
                        gravityScale = 0f
                        isAwake      = false
                    }
                }
            }
        }
        coroutine?.launch {
            asteroidFlow.collect { bItem ->
                delay((1000L..2000L).random())
                runGDX {
                    bItem.body?.apply {
                        setTransform(getRandomAsteroidX, position.y,0f)
                        gravityScale = (7..10).random() / 10f
                        isAwake      = true
                    }
                }
                delay(100)
                bItem.atomicBoolean.set(true)
                bItem.setOriginalId()
            }
        }
    }

    private fun createB_Rocket() {
        bRocket.create(478f, 258f, 123f, 404f)

        val minX = 175f.toB2 + bRocket.center.x
        val maxX = 801f.toB2 + bRocket.center.x

//        bRocket.renderBlockArray.add(AbstractBody.RenderBlock {
//            if (bRocket.body!!.position.x <= minX || bRocket.body!!.position.x >= maxX) bRocket.body?.setLinearVelocity(0f, 0f)
//        })

        bRocket.beginContactBlockArray.add(AbstractBody.ContactBlock { enemy, contact ->
            if (enemy.id == BodyId.ASTEROID) {
                (enemy as BAsteroid).startEffect(contact.worldManifold.points.first().toUI)
            }
        })
    }

    private fun createB_Wheel() {
        bWheel.create(729f, 23f, 292f, 292f)
    }

    private fun createB_Static() {
        bStatic.create(0f, 0f, 1f, 1f)
    }

    // ---------------------------------------------------
    // create Joint
    // ---------------------------------------------------

    private fun createJ_Revolute() {
        jRevolute.create(RevoluteJointDef().apply {
            bodyA = bStatic.body
            bodyB = bWheel.body

            localAnchorA.set(Vector2(875f, 169f).toB2)
        })

        var counter = 0f
        var angle   = 0f
        bWheel.renderBlockArray.add(AbstractBody.RenderBlock {
            counter += it
            if (counter >= 0.2f) {
                counter = 0f

                if (
                    jRevolute.joint!!.jointAngle > (angle + 0.5f) ||
                    jRevolute.joint!!.jointAngle < (angle - 0.5f)
                ) {
                    game.soundUtil.apply { play(CRC.random(), 2.5f) }
                    angle = jRevolute.joint!!.jointAngle
                }

            }
        })
    }

    private fun createJ_Prismatic() {
        jPrismatic.create(PrismaticJointDef().apply {
            bodyA = bStatic.body
            bodyB = bRocket.body

            localAnchorA.set(Vector2(478f, 383f).toB2)

            enableLimit = true
            lowerTranslation = (-478f).toB2
            upperTranslation = 478f.toB2
        })
    }

    private fun createJ_Gear() {
        jGear.create(GearJointDef().apply {
            bodyA = bWheel.body
            bodyB = bRocket.body

            joint1 = jRevolute.joint
            joint2 = jPrismatic.joint
        })
    }


}