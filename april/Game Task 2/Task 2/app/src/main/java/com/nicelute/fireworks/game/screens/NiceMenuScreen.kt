package com.nicelute.fireworks.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor
import com.nicelute.fireworks.game.LibGDXGame
import com.nicelute.fireworks.game.utils.actor.animHide
import com.nicelute.fireworks.game.utils.actor.setOnClickListener
import com.nicelute.fireworks.game.utils.advanced.AdvancedScreen
import com.nicelute.fireworks.game.utils.advanced.AdvancedStage
import com.nicelute.fireworks.game.utils.region

class NiceMenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val menu = Image(game.allAssets.Menu)

    override fun show() {
        setBackBackground(game.allAssets.firre.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenuImg()
        addBtns()
        addExit()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addMenuImg() {
        addActor(menu)
        menu.setBounds(163f, 65f, 754f, 1728f)
    }

    private fun AdvancedStage.addExit() {
        val exit = Actor()
        addActor(exit)
        exit.setBounds(376f, 65f, 328f, 89f)
        exit.setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
    }

    private fun AdvancedStage.addBtns() {
        var nx = 163f
        var ny = 1207f

        listOf(
            NiceFireScreen1::class.java.name,
            NiceFireScreen2::class.java.name,
            NiceFireScreen3::class.java.name,
            NiceFireScreen4::class.java.name,
            NiceFireScreen5::class.java.name,
            NiceFireScreen6::class.java.name,
        ).onEachIndexed { index, className ->
            val actor = Actor()
            addActor(actor)
            actor.setBounds(nx, ny, 323f, 323f)
            nx += 108+323
            if (index.inc() % 2 == 0) {
                nx = 163f
                ny -= 86+323
            }

            actor.setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(0.49f) {
                    game.navigationManager.navigate(className, NiceMenuScreen::class.java.name)
                }
            }
        }
    }

}