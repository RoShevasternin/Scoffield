package com.fluxton.ballerina.game.screens.levels

import com.badlogic.gdx.math.Vector2
import com.fluxton.ballerina.game.LibGDXGame
import com.fluxton.ballerina.game.box2d.bodies.BBall
import com.fluxton.ballerina.game.box2d.bodies.BCoin
import com.fluxton.ballerina.game.box2d.bodies.BRect
import com.fluxton.ballerina.game.box2d.bodies.BTri
import com.fluxton.ballerina.game.utils.TIME_ANIM
import com.fluxton.ballerina.game.utils.actor.animHide
import com.fluxton.ballerina.game.utils.actor.animShow
import com.fluxton.ballerina.game.utils.advanced.AdvancedStage
import com.fluxton.ballerina.game.utils.region

class _3_Screen(_game: LibGDXGame): Ilevel(_game) {

    private val bRectList = List(4) { BRect(this, game.allAssets.listP[2]) }
    private val bCoinList = List(2) { BCoin(this) }
    private val bTriiList = List(2) { BTri(this, game.allAssets.listT[2]) }
    override val bBall    = BBall(this)

    override fun show() {
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.startAssets._3.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStage() {
        createB_Rect()
        createB_Coin()
        createB_Tri()
        createB_Ball()
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Rect() {
        listOf(
            Vector2(618f, 1469f),
            Vector2(-78f, 945f),
            Vector2(686f, 484f),
            Vector2(-34f, 324f),
        ).onEachIndexed { index, pos ->
            bRectList[index].create(pos, Vector2(503f, 81f))
        }
    }

    private fun createB_Coin() {
        listOf(
            Vector2(44f, 1035f),
            Vector2(173f, 420f),
        ).onEachIndexed { index, pos ->
            bCoinList[index].create(pos, Vector2(127f, 127f))
        }
    }

    private fun createB_Tri() {
        listOf(
            Vector2(228f, 1016f),
            Vector2(794f, 565f),
        ).onEachIndexed { index, pos ->
            bTriiList[index].create(pos, Vector2(197f, 185f))
        }
    }

    private fun createB_Ball() {
        bBall.create(673f, 1557f, 214f, 214f)
    }

}