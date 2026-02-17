package com.fluxton.ballerina.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fluxton.ballerina.game.LibGDXGame
import com.fluxton.ballerina.game.screens.levels._1_Screen
import com.fluxton.ballerina.game.screens.levels._2_Screen
import com.fluxton.ballerina.game.screens.levels._3_Screen
import com.fluxton.ballerina.game.screens.levels._4_Screen
import com.fluxton.ballerina.game.utils.TIME_ANIM
import com.fluxton.ballerina.game.utils.actor.animHide
import com.fluxton.ballerina.game.utils.actor.animShow
import com.fluxton.ballerina.game.utils.actor.setBounds
import com.fluxton.ballerina.game.utils.actor.setOnClickListener
import com.fluxton.ballerina.game.utils.advanced.AdvancedScreen
import com.fluxton.ballerina.game.utils.advanced.AdvancedStage
import com.fluxton.ballerina.game.utils.region

class PinkYrowniScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val panelImg = Image(game.allAssets.maps)

    override fun show() {
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.startAssets._3.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addBtns()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(154f, 335f, 764f, 1406f)
    }

    private fun AdvancedStage.addBtns() {
        val sizik = Vector2(223f, 230f)

        val levelName = listOf(
            _1_Screen::class.java.name,
            _2_Screen::class.java.name,
            _3_Screen::class.java.name,
            _4_Screen::class.java.name,
        )

        arrayOf(
            Vector2(177f, 1494f),
            Vector2(672f, 1207f),
            Vector2(194f, 780f),
            Vector2(672f, 355f),
        ).onEachIndexed { index, pos ->
            addActor(Actor().apply {
                setBounds(pos, sizik)
                setOnClickListener(game.soundUtil) {
                    stageUI.root.animHide(TIME_ANIM) {
                        INDEX = index
                        game.navigationManager.navigate(levelName[index])
                    }
                }
            })
        }
    }

}