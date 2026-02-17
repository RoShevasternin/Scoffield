package com.icertif.pyrzzle.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.icertif.pyrzzle.game.LibGDXGame
import com.icertif.pyrzzle.game.actors.AButton
import com.icertif.pyrzzle.game.actors.checkbox.ACheckBox
import com.icertif.pyrzzle.game.actors.progress.ATimer
import com.icertif.pyrzzle.game.actors.puzzle.APuzzlePanel
import com.icertif.pyrzzle.game.utils.advanced.AdvancedScreen
import com.icertif.pyrzzle.game.utils.advanced.AdvancedStage
import com.icertif.pyrzzle.game.utils.puzzle.Puzzles
import com.icertif.pyrzzle.game.utils.region

class GameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val puzzleRegion = game.allAssets.pazles.random()

    private val levelLabel   = Image(game.allAssets.levels[LevelScreen.STATIC_LEVEL])
    private val timer        = ATimer(this)
    private val panelImg     = Image(game.allAssets.gamek)

    private val puzzlesі = APuzzlePanel(this, puzzleRegion)
    private val puzzleImg    = Image(puzzleRegion)

    override fun show() {
        setBackBackground(game.allAssets.bubinka.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addLevelImg()
        addTimer()
        addPanel()
        addMenuBtn()
        addPauseBox()
        addPuzzleі()
        addPuzzleImg()

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

    private fun AdvancedStage.addLevelImg() {
        addActor(levelLabel)
        levelLabel.setBounds(262f, 1709f, 591f, 248f)
    }

    private fun AdvancedStage.addTimer() {
        val tim = Image(game.allAssets.timer_panel)
        addActor(tim)
        tim.setBounds(414f, 1623f, 288f, 132f)

        addActor(timer)
        timer.setBounds(486f, 1631f, 141f, 124f)
    }

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(20f, 44f, 1038f, 1512f)
    }

    private fun AdvancedStage.addMenuBtn() {
        val back = AButton(this@GameScreen, AButton.Static.Type.MENU)
        addActor(back)
        back.setBounds(45f, 1652f, 206f, 206f)
        back.setOnClickListener { animHideScreen { game.navigationManager.back() } }
    }

    private fun AdvancedStage.addPauseBox() {
        val pause = ACheckBox(this@GameScreen, ACheckBox.Static.Type.PAUSE)
        addActor(pause)
        pause.setBounds(865f, 1652f, 206f, 206f)
        pause.setOnCheckListener { timer.isPause = it }
    }

    private fun AdvancedStage.addPuzzleі() {
        addActor(puzzlesі)
        puzzlesі.setBounds(188f, 618f, 700f, 700f)

        puzzlesі.finishBlock = {
            animHideScreen {
                ResultScreen.IS_WIN = true
                game.navigationManager.navigate(ResultScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addPuzzleImg() {
        addActor(puzzleImg)
        puzzleImg.setBounds(412f, 88f, 256f, 256f)
    }

}