package com.secrofthe.lostpaircret.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.secrofthe.lostpaircret.game.utils.actor.animHide
import com.secrofthe.lostpaircret.game.utils.actor.animShow
import com.secrofthe.lostpaircret.game.utils.actor.disable
import com.secrofthe.lostpaircret.game.utils.actor.enable
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedGroup
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedScreen

class ATile(
    override val screen: AdvancedScreen,
    val id: Int,
    val region: TextureRegion
): AdvancedGroup() {

    // Actor
    private val img = Image(region)

    // Field
    private val timeAnim = 0.27f

    override fun addActorsOnGroup() {
        color.a = 0f
        addAndFillActor(img)
    }

    fun animOpen() {
        disable()
        animShow(timeAnim)
    }

    fun animClose() {
        animHide(timeAnim) { enable() }
    }

}