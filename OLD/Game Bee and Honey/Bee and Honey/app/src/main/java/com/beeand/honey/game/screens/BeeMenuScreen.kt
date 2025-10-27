package com.beeand.honey.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.beeand.honey.game.LibGDXGame
import com.beeand.honey.game.utils.TIME_ANIM
import com.beeand.honey.game.utils.actor.animHide
import com.beeand.honey.game.utils.actor.animShow
import com.beeand.honey.game.utils.actor.setOnClickListener
import com.beeand.honey.game.utils.advanced.AdvancedScreen
import com.beeand.honey.game.utils.advanced.AdvancedStage
import com.beeand.honey.game.utils.advanced.isBlue
import com.beeand.honey.game.utils.region

class BeeMenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val playImg = Image(game.allAssets.play)
    private val exitImg = Image(game.allAssets.exit)

    override fun show() {
        isBlue = false
        stageUI.root.animHide()
        setBackBackground(game.startAssets.YELLOW.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(playImg, exitImg)
        playImg.apply {
            setBounds(680f, 260f, 560f, 560f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(BeeGameScreen::class.java.name, BeeMenuScreen::class.java.name)
                }
            }
        }
        exitImg.apply {
            setBounds(1575f, 73f, 293f, 293f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.exit()
                }
            }
        }
    }

}