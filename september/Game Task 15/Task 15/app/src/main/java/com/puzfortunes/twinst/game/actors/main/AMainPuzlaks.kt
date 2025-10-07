package com.puzfortunes.twinst.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzfortunes.twinst.game.actors.button.AButton
import com.puzfortunes.twinst.game.screens.PazzleScreen
import com.puzfortunes.twinst.game.utils.*
import com.puzfortunes.twinst.game.utils.actor.animDelay
import com.puzfortunes.twinst.game.utils.actor.animHide
import com.puzfortunes.twinst.game.utils.actor.animShow
import com.puzfortunes.twinst.game.utils.advanced.AdvancedMainGroup
import com.puzfortunes.twinst.game.actors.puzzle.APuzzlePanel
import com.puzfortunes.twinst.game.screens.WinScreen
import com.puzfortunes.twinst.game.utils.actor.disable
import com.puzfortunes.twinst.game.utils.puzzle.Puzzles
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.puzfortunes.twinst.game.actors.checkbox.ACheckBox
import com.puzfortunes.twinst.game.utils.actor.setOnClickListener

class AMainPuzlaks(override val screen: PazzleScreen): AdvancedMainGroup() {

    private val puzzleRegion = gdxGame.assetsAll.listPuzle.random().region

    private val btnX  = Actor()//AButton(screen, AButton.Type.X)

    private val puzzlesPanel = APuzzlePanel(screen, puzzleRegion)
    private val puzzleImg    = Image(puzzleRegion)

    private val puzzleFrameImg = Image(gdxGame.assetsAll.frame)

    private val musicCB   = ACheckBox(screen, ACheckBox.Type.MUS)
    private val soundCB   = ACheckBox(screen, ACheckBox.Type.SOU)

    override fun addActorsOnGroup() {
        color.a = 0f

        addBtnS()

        addPuzzlePanel()
        addPuzzleImg()

        addMusicCB()
        addSoundCB()

        animShowMain()
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnS() {
        addActor(btnX)
        btnX.setBounds(71f, 1743f, 111f, 115f)

        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(891f, 1723f, 141f, 141f)
            if (gdxGame.musicUtil.currentMusic?.isPlaying == false) check(false)

            setOnCheckListener {
                if (it) {
                    gdxGame.musicUtil.currentMusic?.pause()
                } else {
                    gdxGame.musicUtil.currentMusic?.play()
                }
            }

        }
    }

    private fun addSoundCB() {
        addActor(soundCB)
        soundCB.apply {
            setBounds(48f, 1723f, 141f, 141f)
            if (gdxGame.soundUtil.isPause) check(false)

            setOnCheckListener { gdxGame.soundUtil.isPause = it }

        }
    }

    private fun addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(42f, 129f, 997f, 997f)

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
        addActor(puzzleFrameImg)
        puzzleFrameImg.setBounds(317f, 1272f, 425f, 425f)

        addActor(puzzleImg)
        puzzleImg.setBounds(352f, 1310f, 352f, 352f)
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