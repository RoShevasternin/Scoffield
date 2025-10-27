package com.plinko.ballwinx100.game.actors.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen

class MenuButtons(
    override val screen: AdvancedScreen,
    private val menuButtons: List<MenuButton>,
    private val spacer: Actor = Actor().apply {
        this.height = 64f
    }
    ) : AdvancedGroup() {

    override fun addActorsOnGroup() {
        menuButtons.forEachIndexed { index, button ->
            button.setPosition(
                0f,
                (index * button.height) + (index * spacer.height)
            )
            addAndFillActor(button)
        }
    }

    override fun getWidth(): Float {
        val newWidth = if(menuButtons.isEmpty()) 0f else menuButtons[0].width
        return newWidth
    }

    override fun getHeight(): Float {
        val menuSize = menuButtons.size
        if(menuSize == 0) return 0f
        val menuItemHeight = menuButtons[0].height
        return (menuSize * menuItemHeight) + ((menuSize - 1) * spacer.height)
    }


}