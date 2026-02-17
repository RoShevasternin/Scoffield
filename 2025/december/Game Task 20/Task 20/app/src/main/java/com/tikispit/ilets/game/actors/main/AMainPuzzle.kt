package com.tikispit.ilets.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tikispit.ilets.game.actors.ATimer
import com.tikispit.ilets.game.actors.button.AButton
import com.tikispit.ilets.game.actors.puzzle.APuzzlePanel
import com.tikispit.ilets.game.screens.PuzzleScreen
import com.tikispit.ilets.game.screens.ResultGREENScreen
import com.tikispit.ilets.game.screens.ResultREDScreen
import com.tikispit.ilets.game.utils.Block
import com.tikispit.ilets.game.utils.TIME_ANIM_SCREEN
import com.tikispit.ilets.game.utils.actor.animDelay
import com.tikispit.ilets.game.utils.actor.animHide
import com.tikispit.ilets.game.utils.actor.animShow
import com.tikispit.ilets.game.utils.actor.disable
import com.tikispit.ilets.game.utils.advanced.AdvancedMainGroup
import com.tikispit.ilets.game.utils.gdxGame
import com.tikispit.ilets.game.utils.puzzle.Puzzles
import com.tikispit.ilets.game.utils.region

class AMainPuzzle(override val screen: PuzzleScreen): AdvancedMainGroup() {

    private val puzzleRegion = gdxGame.assetsAll.listPuzzles.random().region

    private val imgPanel  = Image(gdxGame.assetsAll.GAMR)
    private val btnBack   = AButton(screen, AButton.Type.Back)

    private val puzzlesPanel = APuzzlePanel(screen, puzzleRegion)
    private val aTimer       = ATimer(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPanel()
        addBtnBack()
        addPuzzlePanel()
        addATimer()

        animShowMain()
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(76f, 453f, 987f, 1360f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(58f, 1755f, 107f, 92f)

        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(219f, 620f, 640f, 640f)

        puzzlesPanel.finishBlock = {
            this.disable()

            //gdxGame.soundUtil.apply { play(win) }

            animDelay(0.35f) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ResultGREENScreen::class.java.name)
                }
            }
        }
    }

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(443f, 1505f, 193f, 102f)

        aTimer.start()

        aTimer.timeOut = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultREDScreen::class.java.name)
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}