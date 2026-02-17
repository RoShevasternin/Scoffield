package com.plinko.ballwinx100.game.screens.game

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

class PlinkoContactListener(): ContactListener {

    private val baskets = listOf(
        ObjectType.BASKET_0,
        ObjectType.BASKET_1,
        ObjectType.BASKET_2,
        ObjectType.BASKET_3,
    )

    private val ballTriggers = hashMapOf<String, (FixtureUserData, FixtureUserData) -> Unit>()

    fun addBallTrigger(actorName: String, onTrigger: (FixtureUserData, FixtureUserData) -> Unit) {
        ballTriggers[actorName] = onTrigger
    }

    override fun beginContact(contact: Contact?) {
        val fixtureA = contact?.fixtureA
        val fixtureB = contact?.fixtureB

        val userDataA = fixtureA?.userData
        val userDataB = fixtureB?.userData
        if(userDataA is FixtureUserData && userDataB is FixtureUserData) {
            if (userDataA.objectType == ObjectType.BALL) {
                val ballBody = fixtureA.body
                handle(ballBody, userDataA, userDataB)
                /*when (userDataB) {
                ObjectType.PIN -> {
                    val pinType = userDataB as ObjectType
                    ballBody.applyForceToCenter(Vector2(5f, 10f), true)
                    ballTriggers[ballBody]!!(ballType, pinType)
                }
                in baskets -> {
                    val basketType = userDataB as ObjectType
                    ballTriggers[ballBody]!!(ballType, basketType)
                }
                else -> {}
            }*/

            } else {
                val ballBody = fixtureB?.body!!
                handle(ballBody, userDataB, userDataA)
                /*when (userDataA) {
                ObjectType.PIN -> {
                    val pinType = userDataA as ObjectType
                    ballBody.applyForceToCenter(Vector2(5f, 10f), true)
                    ballTriggers[ballBody]!!(ballType, pinType)
                }
                in baskets -> {
                    val basketType = userDataA as ObjectType

                    ballTriggers[ballBody]!!(
                        ballType,
                        basketType
                    )

                }
                else -> {}
            }*/
            }
        }

    }

    override fun endContact(contact: Contact?) {
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }

    fun handle(ballBody: Body, ballUserData: FixtureUserData, nextUserData: FixtureUserData){

        when (nextUserData.objectType) {
            ObjectType.PIN -> {
                ballBody.applyForceToCenter(Vector2(5f, 10f), true)
                ballTriggers[ballUserData.actorName]!!(ballUserData, nextUserData)
            }
            in baskets -> {
                ballTriggers[ballUserData.actorName]!!(
                    ballUserData,
                    nextUserData
                )

            }
            else -> {}
        }
    }
}