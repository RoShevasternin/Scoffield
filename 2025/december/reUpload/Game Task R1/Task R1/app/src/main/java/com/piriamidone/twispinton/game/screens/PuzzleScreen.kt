package com.piriamidone.twispinton.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.piriamidone.twispinton.game.LibGDXGame
import com.piriamidone.twispinton.game.actors.checkbox.ACheckBox
import com.piriamidone.twispinton.game.actors.masks.Mask
import com.piriamidone.twispinton.game.actors.progress.ATimer
import com.piriamidone.twispinton.game.actors.puzzle.APuzzlePanel
import com.piriamidone.twispinton.game.utils.actor.setOnClickListener
import com.piriamidone.twispinton.game.utils.advanced.AdvancedScreen
import com.piriamidone.twispinton.game.utils.advanced.AdvancedStage
import com.piriamidone.twispinton.game.utils.puzzle.Puzzles
import com.piriamidone.twispinton.game.utils.region

class PuzzleScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val puzzleRegion = game.alllAssets.puzzleList.random().region

    private val musicCB      = ACheckBox(this, ACheckBox.Static.Type.MUSIC)
    private val gamerImg     = Image(game.alllAssets.gamer)

    private val timer        = ATimer(this)
    private val puzzlesPanel = APuzzlePanel(this, puzzleRegion)
    private val puzzleImg    = Image(puzzleRegion)
    private val levelLabel   = Image(game.alllAssets.levels[LevelScreen.STATIC_LEVEL])

    override fun show() {
        setBackgrounds(game.loadAssets.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addGamer()
        addMenuBtn()
        addMusicCB()
        addPuzzlePanel()
        addPuzzleImg()
        addLevelImg()
        addTimer()

        val tim = Image(game.alllAssets.time)
        addActor(tim)
        tim.setBounds(205f, 1536f, 90f, 90f)

        timer.startTimer { animHideScreen {
            ResultScreen.IS_WIN = false
            game.navigationManager.navigate(ResultScreen::class.java.name)
        } }
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addGamer() {
        addActor(gamerImg)
        gamerImg.setBounds(87f, 78f, 952f, 1755f)
    }

    private fun AdvancedStage.addMenuBtn() {
        val back = Actor()
        addActor(back)
        back.setBounds(102f, 1671f, 156f, 162f)
        back.setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
    }

    private fun AdvancedStage.addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(883f, 1671f, 156f, 162f)
            if (game.musicUtil.music?.isPlaying == false) check(false)

            setOnCheckListener { isCheck ->
                if (isCheck) game.musicUtil.music?.pause() else game.musicUtil.music?.play()
            }

        }
    }

    private fun AdvancedStage.addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(179f, 656f, 722f, 722f)

        puzzlesPanel.finishBlock = {
            animHideScreen {
                ResultScreen.IS_WIN = true
                game.navigationManager.navigate(ResultScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addPuzzleImg() {
        val maska = Mask(this@PuzzleScreen, game.alllAssets.puzzle_mask)
        addActor(maska)
        maska.setBounds(384f, 127f, 315f, 315f)

        maska.addAndFillActor(puzzleImg)
    }

    private fun AdvancedStage.addLevelImg() {
        addActor(levelLabel)
        levelLabel.setBounds(353f, 1699f, 435f, 106f)
    }

    private fun AdvancedStage.addTimer() {
        addActor(timer)
        timer.setBounds(233f, 1558f, 626f, 51f)
    }

}