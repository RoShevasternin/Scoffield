package com.plinko.ballwinx100.game.actors.bar

import com.plinko.ballwinx100.game.utils.WIDTH_UI
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen

class TopBar(
    override val screen: AdvancedScreen,
    private val startTexture: AdvancedGroup = EmptyGroup(screen),
    private val centerTexture: AdvancedGroup = EmptyGroup(screen),
    private val endTexture: AdvancedGroup = EmptyGroup(screen),
) : AdvancedGroup() {


    override fun addActorsOnGroup() {

        if(startTexture.isVisible){
            startTexture.apply {
                this.setBounds(
                    32f,
                    0f,
                    this.width,
                    this.height
                )
            }
            addActor(startTexture)
        }

        if(endTexture.isVisible){
            endTexture.apply {
                this.setBounds(
                    this@TopBar.width - this.width - 32f,
                    0f,
                    this.width,
                    this.height
                )
            }
            addActor(endTexture)
        }

        if(centerTexture.isVisible){
            centerTexture.apply {
                this.setBounds(
                    WIDTH_UI/2 - this.width/2,
                    this@TopBar.height/2 - this.height/2,
                    this.width,
                    this.height
                )
            }
            addActor(centerTexture)
        }

    }

    override fun getWidth(): Float {
        return WIDTH_UI
    }

    override fun getHeight(): Float {
        var currentHeight = 0f
        if(startTexture.isVisible){
            currentHeight = startTexture.height
        }  else if(endTexture.isVisible){
            currentHeight = endTexture.height
        } else if(centerTexture.isVisible){
            currentHeight = centerTexture.height
        }

        return currentHeight
    }

}