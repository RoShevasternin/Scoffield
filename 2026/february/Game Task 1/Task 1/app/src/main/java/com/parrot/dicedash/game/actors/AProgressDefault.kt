package com.parrot.dicedash.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.parrot.dicedash.game.actors.mask.AOldMask
import com.parrot.dicedash.game.utils.WIDTH_UI
import com.parrot.dicedash.game.utils.advanced.AdvancedGroup
import com.parrot.dicedash.game.utils.advanced.AdvancedScreen
import com.parrot.dicedash.game.utils.gdxGame
import com.parrot.dicedash.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgressLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 663f

    private val backgroundImage = Image(gdxGame.assetsLoader.backpan)
    private val progressImage   = Image(gdxGame.assetsLoader.gradient)
    private val mask            = AOldMask(screen, mask = gdxGame.assetsLoader.MASK, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addBackground()
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX { progressImage.x = (percent * onePercentX) - LENGTH }
            }
        }
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addBackground() {
        addAndFillActor(backgroundImage)
    }

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(34f, 30f, 663f, 63f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addAndFillActor(progressImage)
    }

}