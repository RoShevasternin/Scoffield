package com.parrot.dicedash.game.screens

import com.badlogic.gdx.math.Interpolation
import com.parrot.dicedash.game.actors.button.AButton
import com.parrot.dicedash.game.utils.Acts
import com.parrot.dicedash.game.utils.Block
import com.parrot.dicedash.game.utils.actor.Bounds
import com.parrot.dicedash.game.utils.actor.animDelay
import com.parrot.dicedash.game.utils.actor.setBounds
import com.parrot.dicedash.game.utils.actor.setOnClickListener
import com.parrot.dicedash.game.utils.actor.setPosition
import com.parrot.dicedash.game.utils.advanced.AdvancedScreen
import com.parrot.dicedash.game.utils.advanced.AdvancedStage
import com.parrot.dicedash.game.utils.gdxGame
import com.parrot.dicedash.util.log

class MenuScreen: AdvancedScreen() {

    private val listBtnType = listOf(
        AButton.Type.Start,
        AButton.Type.HTP,
        AButton.Type.PP,
        AButton.Type.LEADERBOARD,
        AButton.Type.Exit,
    )
    private val listBtnActor = List(listBtnType.size) { AButton(this, listBtnType[it]) }

    // Field
    private val listBtnBounds = listOf(
        Bounds(194f, 1269f, 692f, 266f),
        Bounds(261f, 1032f, 558f, 214f),
        Bounds(261f, 795f, 558f, 214f),
        Bounds(261f, 558f, 558f, 214f),
        Bounds(342f, 384f, 395f, 151f),
    )

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        //root.color.a = 0f

        addListBtn()

        animShow {
            //coroutine?.launch { addLevels() }
        }
    }

    override fun animShow(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }
        var timeDelay = 0f

        timeDelay = animShowListBtn()

        //stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(timeDelay) { blockEnd() }
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }
        var timeDelay = 0f

        timeDelay = animHideListBtn()

        //stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(timeDelay) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addListBtn() {
        val listBtnBlock = listOf(
            { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) },
            { gdxGame.navigationManager.navigate(HTPScreen::class.java.name, MenuScreen::class.java.name) },
            { gdxGame.navigationManager.navigate(LeaderboardScreen::class.java.name, MenuScreen::class.java.name) },
            { gdxGame.activity.openPrivacyPolicy() },
            { gdxGame.navigationManager.exit() },
        )

        listBtnActor.forEachIndexed { index, button ->
            addActor(button)
            val bounds = listBtnBounds[index]
            button.setBounds(-bounds.w - 100f, 0f, bounds.w, bounds.h)

            button.setOnClickListener {
                if (index == 3) listBtnBlock[index].invoke() else animHide { listBtnBlock[index].invoke() }
            }
        }
    }

    // Anim ------------------------------------------------------------------------

    private fun animShowListBtn(): Float {
        var timeDelay = 0f
        listBtnActor.forEachIndexed { index, button ->
            val bounds = listBtnBounds[index]
            button.clearActions()
            button.animDelay(if (timeDelay != 0f) timeDelay / 2f else 0f) {
                button.addAction(Acts.moveTo(bounds.x, bounds.y, 0.5f, Interpolation.swingOut))
            }
            timeDelay += 0.5f
        }

        return timeDelay
    }

    private fun animHideListBtn(): Float {
        var timeDelay = 0f
        listBtnActor.forEachIndexed { index, button ->
            val bounds = listBtnBounds[index]
            button.clearActions()
            button.animDelay(if (timeDelay != 0f) timeDelay / 2f else 0f) {
                button.addAction(Acts.moveTo(-bounds.w - 100f, 0f, 0.3f, Interpolation.swingIn))
            }
            timeDelay += 0.3f
        }

        return timeDelay / 1.1f
    }

}