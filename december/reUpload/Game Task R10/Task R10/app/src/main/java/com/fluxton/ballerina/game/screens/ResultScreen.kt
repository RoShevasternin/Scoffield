package com.fluxton.ballerina.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fluxton.ballerina.game.LibGDXGame
import com.fluxton.ballerina.game.utils.TIME_ANIM
import com.fluxton.ballerina.game.utils.actor.animHide
import com.fluxton.ballerina.game.utils.actor.animShow
import com.fluxton.ballerina.game.utils.actor.setOnClickListener
import com.fluxton.ballerina.game.utils.advanced.AdvancedScreen
import com.fluxton.ballerina.game.utils.advanced.AdvancedStage
import com.fluxton.ballerina.game.utils.region

var IS_WIN = true
var INDEX  = 0

class ResultScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val panelImg = Image(if (IS_WIN) game.allAssets.win else game.allAssets.lose)

    override fun show() {
        if (IS_WIN) game.soundUtil.apply { play(WIN) } else game.soundUtil.apply { play(LOSE) }
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(
            when(INDEX) {
                0 -> game.allAssets._1.region
                1 -> game.allAssets._2.region
                2 -> game.startAssets._3.region
                3 -> game.allAssets._4.region
                else -> game.allAssets._1.region
            }
        )
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addButtons()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(118f, 344f, 845f, 1362f)
    }

    private fun AdvancedStage.addButtons() {
        var ny = 564f
        arrayOf(
            "EXIT",
            PinkRulesScreen::class.java.name,
            PinkSettingsScreen::class.java.name,
            PinkYrowniScreen::class.java.name,
        ).onEach { sName ->
            addActor(Actor().apply {
                setBounds(293f, ny, 544f, 137f)
                ny += (80+137)

                setOnClickListener(game.soundUtil) {
                    if (sName == "EXIT") game.navigationManager.exit()
                    else stageUI.root.animHide(TIME_ANIM) {
                        game.navigationManager.navigate(sName)
                    }
                }
            })
        }
    }

}