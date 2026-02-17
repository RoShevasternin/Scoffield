package com.dliw.drib.game.box2d.bodiesGroup

import com.dliw.drib.game.box2d.AbstractBodyGroup
import com.dliw.drib.game.box2d.BodyId
import com.dliw.drib.game.box2d.bodies.BHorizontal
import com.dliw.drib.game.utils.advanced.AdvancedBox2dScreen

class BGBorders(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = 1920f

    val bTop   = BHorizontal(screenBox2d)
    val bDown  = BHorizontal(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_Borders()

        createHorizontal()
    }


    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Borders() {
        arrayOf(bTop, bDown).onEach { it.apply {
            id = BodyId.BORDERS
            collisionList.addAll(arrayOf(
                BodyId.Coyntry.BIRD,
            ))
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createHorizontal() {
        createBody(bTop, 0f, 1080f, 1920f, 10f)
        createBody(bDown, 0f, -10f, 1920f, 10f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------


}