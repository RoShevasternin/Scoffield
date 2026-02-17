package com.hexofun.rundeo.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.hexofun.rundeo.game.LibGDXGame
import com.hexofun.rundeo.game.utils.TIME_ANIM
import com.hexofun.rundeo.game.utils.actor.animHide
import com.hexofun.rundeo.game.utils.actor.setOnClickListener
import com.hexofun.rundeo.game.utils.advanced.AdvancedScreen
import com.hexofun.rundeo.game.utils.advanced.AdvancedStage
import com.hexofun.rundeo.game.utils.region

class HexagonMapScreen(override val game: LibGDXGame): AdvancedScreen() {

    companion object {
        var indexMap = 0
            private set
    }

    private val backgrounds = listOf(
        game.startAssets.BACKGROUND,
        game.allAssets._2,
        game.allAssets._3,
        game.allAssets._4,
    )

    private val panelImg      = Image(game.allAssets.map)
    private val backgroundImg = Image(backgrounds[indexMap])
    private val left          = Actor()
    private val right         = Actor()

    override fun show() {
        setBackBackground(game.startAssets.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addExit()
        addBackground()
        addBtns()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(519f, 234f, 882f, 726f)
    }

    private fun AdvancedStage.addExit() {
        val exit = Actor()
        addActors(exit)
        exit.setBounds(1227f, 234f, 130f, 133f)
        exit.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addBackground() {
        addActor(backgroundImg)
        backgroundImg.setBounds(761f, 465f, 399f, 225f)

        backgroundImg.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(HexagonGameScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(left, right)
        left.setBounds(616f, 520f, 113f, 97f)
        left.setOnClickListener(game.soundUtil) {
            indexMap = if (indexMap-1 >= 0) indexMap-1 else backgrounds.lastIndex
            backgroundImg.drawable = TextureRegionDrawable(backgrounds[indexMap])
        }
        right.setBounds(1211f, 520f, 113f, 97f)
        right.setOnClickListener(game.soundUtil) {
            indexMap = if (indexMap+1 <= backgrounds.lastIndex) indexMap+1 else 0
            backgroundImg.drawable = TextureRegionDrawable(backgrounds[indexMap])
        }
    }

}