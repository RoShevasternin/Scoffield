package com.appwin.fight.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.appwin.fight.game.actors.button.AButton
import com.appwin.fight.game.screens.PazzleScreen
import com.appwin.fight.game.utils.*
import com.appwin.fight.game.utils.actor.animDelay
import com.appwin.fight.game.utils.actor.animHide
import com.appwin.fight.game.utils.actor.animShow
import com.appwin.fight.game.utils.advanced.AdvancedMainGroup
import com.appwin.fight.game.actors.puzzle.APuzzlePanel
import com.appwin.fight.game.screens.WinScreen
import com.appwin.fight.game.utils.actor.disable
import com.appwin.fight.game.utils.puzzle.Puzzles
import com.badlogic.gdx.graphics.g2d.TextureRegion

var STAT_REG: TextureRegion? = null

class AMainPuzlaks(override val screen: PazzleScreen): AdvancedMainGroup() {

    private val puzzleRegion = gdxGame.assetsAll.listPuzle.random().region

    private val btnX  = AButton(screen, AButton.Type.X)

    private val puzzlesPanel = APuzzlePanel(screen, puzzleRegion)
    private val puzzleImg    = Image(puzzleRegion)

    override fun addActorsOnGroup() {
        color.a = 0f

        addBtnS()

        addPuzzlePanel()
        addPuzzleImg()

        animShowMain()

        STAT_REG = puzzleRegion
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnS() {
        addActor(btnX)
        btnX.setBounds(71f, 1743f, 111f, 115f)

        btnX.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(95f, 218f, 887f, 887f)

        puzzlesPanel.finishBlock = {
            this.disable()

            gdxGame.soundUtil.apply { play(win) }

            animDelay(0.75f) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(WinScreen::class.java.name)
                }
            }
        }
    }

    private fun addPuzzleImg() {
        addActor(puzzleImg)
        puzzleImg.setBounds(325f, 1217f, 428f, 428f)
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