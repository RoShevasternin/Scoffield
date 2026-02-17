package com.fortunepieces.goldrushbig.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fortunepieces.goldrushbig.game.actors.ATimer
import com.fortunepieces.goldrushbig.game.actors.button.AButton
import com.fortunepieces.goldrushbig.game.actors.checkbox.ACheckBox
import com.fortunepieces.goldrushbig.game.actors.puzzle.APuzzlePanel
import com.fortunepieces.goldrushbig.game.screens.FailScreen
import com.fortunepieces.goldrushbig.game.screens.PazzleScreen
import com.fortunepieces.goldrushbig.game.screens.WinScreen
import com.fortunepieces.goldrushbig.game.utils.Block
import com.fortunepieces.goldrushbig.game.utils.TIME_ANIM_SCREEN
import com.fortunepieces.goldrushbig.game.utils.actor.*
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedMainGroup
import com.fortunepieces.goldrushbig.game.utils.gdxGame
import com.fortunepieces.goldrushbig.game.utils.puzzle.Puzzles

class AMainPuzlaks(override val screen: PazzleScreen): AdvancedMainGroup() {

    private val puzzleRegion = (GLOB_REG as TextureRegionDrawable).region  //gdxGame.assetsAll.listPuzle.random().region

    private val btnArr   = AButton(screen, AButton.Type.Back)
    private val boxPause = ACheckBox(screen, ACheckBox.Type.PAUSE_PLAY)

    private val puzzlesPanel = APuzzlePanel(screen, puzzleRegion)
    private val imgText      = Image(gdxGame.assetsAll.HUARAP)

    private val aTimer = ATimer(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addBtnBack()
        addPuzzlePanel()
        addPause()
        addImgText()
        addATimer()

        animShowMain()
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnArr)
        btnArr.setBounds(56f, 903f, 114f, 114f)

        btnArr.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgText() {
        addActor(imgText)
        imgText.setBounds(425f, 924f, 1070f, 76f)
    }

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(828f, 40f, 264f, 96f)

        aTimer.start()

        aTimer.timeOut = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(FailScreen::class.java.name)
            }
        }
    }

    private fun addPause() {
        addActor(boxPause)
        boxPause.apply {
            setBounds(1751f, 903f, 114f, 114f)
            if (gdxGame.musicUtil.currentMusic?.isPlaying == false) check(false)

            setOnCheckListener {
                if (it) {
                    aTimer.stop()
                } else {
                    aTimer.resume()
                }
            }

        }
    }

    private fun addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(613f, 193f, 695f, 693f)

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