package com.plinko.ballwinx100.game.screens

import com.plinko.ballwinx100.R
import com.plinko.ballwinx100.game.LibGDXGame
import com.plinko.ballwinx100.game.actors.button.MenuButton
import com.plinko.ballwinx100.game.actors.button.MenuButtons
import com.plinko.ballwinx100.game.actors.dialog.SettingsDialog
import com.plinko.ballwinx100.game.screens.game.GameScreen
import com.plinko.ballwinx100.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.plinko.ballwinx100.game.utils.actor.animHide
import com.plinko.ballwinx100.game.utils.actor.setOnClickListener
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.advanced.AdvancedStage

class HomeScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val settingsDialog: SettingsDialog = SettingsDialog(this)

    override fun show() {
        setBackgrounds(game.mainAssets.BACK_2)
        super.show()
    }

    override fun render(delta: Float) {
        super.render(delta)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenuButtons()
    }

    private fun addMenuButtons() {
        val menuButtonsList = listOf(
            MenuButton(
                game.mainAssets.LABEL_MENU_EXIT,
                this
            ).apply {
                this.setOnClickListener(game.soundUtil) {
                    navigateGo("EXIT")
                }
            },
            MenuButton(
                game.mainAssets.LABEL_MENU_ABOUT,
                this
            ).apply {
                this.setOnClickListener(game.soundUtil) {
                    game.activity.plmFrame.shouldCloseWebViewOnBack = true
                    game.activity.plmFrame.firstOpen = false
                    game.activity.plmFrame.showAndOpenUrl(
                        game.activity.getString(R.string.JUjZ3h)
                    )
                    game.activity.plmFrame.showWebView()
                }
            },
            MenuButton(
                game.mainAssets.LABEL_MENU_SETTINGS,
                this
            ).apply {
                this.setOnClickListener(game.soundUtil) {
                    val onHomeClick: () -> Unit = {
                        settingsDialog.remove()
                    }
                    settingsDialog.onHomeClick = onHomeClick

                    stageUI.addAndFillActor(settingsDialog)
                }
            },
            MenuButton(
                game.mainAssets.LABEL_MENU_PLAY,
                this
            ).apply {
                this.setOnClickListener(game.soundUtil) {
                    navigateGo(GameScreen::class.java.name)
                }
            }
        )

        val menuButtons = MenuButtons(
            this,
            menuButtonsList
        )

        menuButtons.setBounds(
            stageUI.width / 2 - menuButtons.width / 2,
            (stageUI.height / 2 - menuButtons.height / 2) - 50f,
            menuButtons.width,
            menuButtons.height
        )

        stageUI.addActor(menuButtons)
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