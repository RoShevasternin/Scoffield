package com.plinko.ballwinx100.game.screens.game

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.plinko.ballwinx100.game.LibGDXGame
import com.plinko.ballwinx100.game.actors.bar.TopBar
import com.plinko.ballwinx100.game.actors.button.MoneyPanel
import com.plinko.ballwinx100.game.actors.button.TopIconButton
import com.plinko.ballwinx100.game.actors.dialog.GameOverDialog
import com.plinko.ballwinx100.game.actors.dialog.GameStartDialog
import com.plinko.ballwinx100.game.screens.HomeScreen
import com.plinko.ballwinx100.game.utils.HEIGHT_UI
import com.plinko.ballwinx100.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.plinko.ballwinx100.game.utils.actor.animHide
import com.plinko.ballwinx100.game.utils.actor.setOnClickListener
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.advanced.AdvancedStage

class GameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val TAG = "GameScreen"

    private val gameOverDialog: GameOverDialog = GameOverDialog(this)
    private val gameStartDialog: GameStartDialog = GameStartDialog(this)

    var world: World? = null
    var scene: PlinkoScene? = null

    override fun show() {
        setBackgrounds(game.mainAssets.BACK_3)
        super.show()

    }

    override fun render(delta: Float) {
        //Gdx.gl.glClearColor(0f, 0f, 0f, 0f) //
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        world?.step(1/60f, 6, 2)
        scene?.renderDebug(stageUI.camera)
        super.render(delta)
    }


    override fun AdvancedStage.addActorsOnStageUI() {
        addTopViews()

        gameStartDialog.onHomeClick = {
            navigateGo(HomeScreen::class.java.name)
        }
        gameStartDialog.onPlayClick = {
            gameStartDialog.remove()

            val inWorld = World(Vector2(0f, -9.8f), true)
            val scene = PlinkoScene(this@GameScreen, inWorld) {

                gameOverDialog.onHomeClick = {
                    navigateGo(HomeScreen::class.java.name)
                }
                gameOverDialog.onNextClick = {
                    navigateGo(GameScreen::class.java.name)
                }
                stageUI.addAndFillActor(gameOverDialog)
            }

            this@GameScreen.world = inWorld
            this@GameScreen.scene = scene

        }

        stageUI.addAndFillActor(gameStartDialog)
             //addUserController()
    }

    private fun addTopViews() {
        val topBar = TopBar(
            this,
            startTexture = TopIconButton(
                this,
                game.mainAssets.EMPTY,
                game.mainAssets.BUTTON_HOME
            ).apply {
                this.setOnClickListener(game.soundUtil) {
                    navigateGo(HomeScreen::class.java.name)
                }
            },
            centerTexture = MoneyPanel(
                this,
                game.mainAssets.BACK_SCORE
            )
        ).apply {
            setBounds(
                0f,
                HEIGHT_UI - this.height - 32f,
                this.width,
                this.height
            )
        }
        topBar.name = "topBar"
        stageUI.addActor(topBar)

    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navigateGo(id: String) {
        stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
            if (id == "EXIT") game.navigationManager.exit() else game.navigationManager.navigate(
                id,
                this::class.java.name
            )
        }
    }

}