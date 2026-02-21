package com.junglesort.questern.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.junglesort.questern.game.actors.ATmpGroup
import com.junglesort.questern.game.utils.Block
import com.junglesort.questern.game.utils.TIME_ANIM_SCREEN
import com.junglesort.questern.game.utils.actor.addActorWithConstraints
import com.junglesort.questern.game.utils.actor.addActors
import com.junglesort.questern.game.utils.actor.addAndFillActor
import com.junglesort.questern.game.utils.actor.animDelay
import com.junglesort.questern.game.utils.actor.animHide
import com.junglesort.questern.game.utils.actor.animShow
import com.junglesort.questern.game.utils.actor.setOnClickListener
import com.junglesort.questern.game.utils.advanced.AdvancedScreen
import com.junglesort.questern.game.utils.font.FontParameter
import com.junglesort.questern.game.utils.gdxGame

class LoseScreen: AdvancedScreen() {

    private val group = ATmpGroup(this)

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontTitle = fontGenerator_Regular.generateFont(parameter.setSize(44))

    private val imgPanel = Image(gdxGame.assetsAll.WIN_PAN)
    private val lblTitle = Label("0", Label.LabelStyle(fontTitle, Color.WHITE))

    override fun show() {
        gdxGame.soundUtil.apply { play(lose_game) }

        stageUI.root.color.a = 0f
        setBackBackground(gdxGame.assetsAll.LOSE)
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        addGroup()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addGroup() {
        group.setSize(583f, 179f)
        addActorWithConstraints(group) {
            startToStartOf   = this@addGroup
            endToEndOf       = this@addGroup
            topToTopOf       = this@addGroup
            bottomToBottomOf = this@addGroup

            verticalBias = 0.15f
        }

        group.apply {
            addAndFillActor(imgPanel)

            //addActor(lblTitle)
            //lblTitle.setBounds(520f, 466f, 32f, 33f)

            val aM = Actor()
            val aR = Actor()
            val aP = Actor()
            addActors(aM, aR, aP)
            aM.setBounds(0f, 0f, 179f, 179f)
            aR.setBounds(202f, 0f, 179f, 179f)
            aP.setBounds(404f, 0f, 179f, 179f)
            aM.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
            aR.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
            aP.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }

    }

}