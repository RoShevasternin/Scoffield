package com.nicelute.fireworks.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor
import com.nicelute.fireworks.game.LibGDXGame
import com.nicelute.fireworks.game.utils.actor.animShow
import com.nicelute.fireworks.game.utils.actor.setOnClickListener
import com.nicelute.fireworks.game.utils.advanced.AdvancedScreen
import com.nicelute.fireworks.game.utils.advanced.AdvancedStage
import com.nicelute.fireworks.game.utils.region

class NiceFireScreen4(override val game: LibGDXGame) : AdvancedScreen() {

    private val particle1 = ParticleEffectActor(game.particleEffectUtil.F4_SALUT_1, true)
    private val particle2 = ParticleEffectActor(game.particleEffectUtil.F2_SALUT_2, true)

    private val btn = Image(game.allAssets.btn)

    override fun show() {
        setBackBackground(game.allAssets.backs[3].region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtnImg()
        addBtns()

        addParticle()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addBtnImg() {
        addActor(btn)
        btn.setBounds(376f, 65f, 328f, 377f)
    }

    private fun AdvancedStage.addBtns() {
        val exit = Actor()
        addActor(exit)
        exit.setBounds(376f, 65f, 328f, 89f)
        exit.setOnClickListener(game.soundUtil) { game.navigationManager.back() }

        val play = Actor()
        addActor(play)
        play.setBounds(439f, 239f, 203f, 203f)
        play.setOnClickListener(game.soundUtil) {
            game.soundUtil.apply { play(START) }

            particle1.apply {
                clearActions()
                setPosition(540f, 442f)
                animShow(0.2f)

                start()
                addAction(Actions.sequence(
                    Actions.moveTo(540f, 1342f, 4f, Interpolation.pow3OutInverse),
                    Actions.run {
                        game.soundUtil.apply { play(fires.random()) }

                        particle1.allowCompletion()
                        particle1.cancel()
                        particle2.start()
                    },
                    Actions.fadeOut(0.3f)
                ))
            }
        }
    }

    private fun AdvancedStage.addParticle() {
        addActors(particle1, particle2)
        particle1.setPosition(540f, 442f)
        particle2.setPosition(540f, 1342f)

    }

}