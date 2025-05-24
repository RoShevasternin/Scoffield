package com.bonanza.pazzleground.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bonanza.pazzleground.game.actors.ATimer
import com.bonanza.pazzleground.game.actors.button.AButton
import com.bonanza.pazzleground.game.actors.checkbox.ACheckBox
import com.bonanza.pazzleground.game.screens.PazzleScreen
import com.bonanza.pazzleground.game.utils.*
import com.bonanza.pazzleground.game.utils.actor.animDelay
import com.bonanza.pazzleground.game.utils.actor.animHide
import com.bonanza.pazzleground.game.utils.actor.animShow
import com.bonanza.pazzleground.game.utils.advanced.AdvancedMainGroup
import com.bonanza.pazzleground.game.actors.puzzle.APuzzlePanel
import com.bonanza.pazzleground.game.utils.actor.disable
import com.bonanza.pazzleground.game.utils.puzzle.Puzzles

class AMainPuzlaks(override val screen: PazzleScreen): AdvancedMainGroup() {

    private val puzzleRegion = gdxGame.assetsAll.listPuziks.random().region

    private val btnMenu  = AButton(screen, AButton.Type.Menu)
    private val btnPause = ACheckBox(screen, ACheckBox.Type.PAUSE)

    private val puzzlesPanel = APuzzlePanel(screen, puzzleRegion)
    private val puzzleImg    = Image(puzzleRegion)

    private val timer     = ATimer(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addPuse()
        addBtnS()

        addPuzzlePanel()
        addPuzzleImg()

        addTimer()

        animShowMain {
            timer.startTimer {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(PazzleScreen::class.java.name)
                }
            }
        }
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnS() {
        addActor(btnMenu)
        btnMenu.setBounds(43f, 1729f, 156f, 162f)

        btnMenu.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addPuse() {
        addActor(btnPause)
        btnPause.setBounds(891f, 1729f, 156f, 162f)

        btnPause.setOnCheckListener { timer.isPause = it }
    }

    private fun addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(60f, 557f, 961f, 961f)

        puzzlesPanel.finishBlock = {
            this.disable()

            animDelay(0.75f) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(PazzleScreen::class.java.name)
                }
            }
        }
    }

    private fun addPuzzleImg() {
        addActor(puzzleImg)
        puzzleImg.setBounds(327f, 60f, 426f, 426f)
    }

    private fun addTimer() {
        addActor(timer)
        timer.setBounds(212f, 1546f, 666f, 90f)
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