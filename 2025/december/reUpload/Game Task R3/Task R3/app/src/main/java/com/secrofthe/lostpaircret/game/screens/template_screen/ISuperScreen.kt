package com.secrofthe.lostpaircret.game.screens.template_screen

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.secrofthe.lostpaircret.game.LibGDXGame
import com.secrofthe.lostpaircret.game.screens.template_screen.ISuperScreen.Static.TypeScreen.*
import com.secrofthe.lostpaircret.game.utils.TIME_ANIM_T
import com.secrofthe.lostpaircret.game.utils.actor.animHide
import com.secrofthe.lostpaircret.game.utils.actor.setOnClickListener
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedScreen
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedStage
import com.secrofthe.lostpaircret.game.utils.region

abstract class ISuperScreen(
    final override val game: LibGDXGame,
    val typeScreen: Static.TypeScreen,
) : AdvancedScreen() {

    private val panelImg = Image(getPanelByTypeScreen())

    var xBlock: () -> Unit = { stageUI.root.animHide(TIME_ANIM_T) { game.navigationManager.back() } }

    override fun show() {
        setBackBackground(game.loadingAssets.matrix.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelImg()

        val xBtn = Actor()
        addActor(xBtn)
        xBtn.apply {
            setBounds(783f, 395f, 276f, 276f)
            setOnClickListener(game.soundUtil) { xBlock() }
        }

        addActorsOnStage()
    }

    abstract fun AdvancedStage.addActorsOnStage()

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.setBounds(94f, 395f, 966f, 1128f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getPanelByTypeScreen(): TextureRegion = when(typeScreen) {
        Menu    -> game.allAssets.menu.region
        Rules   -> game.allAssets.rules.region
        Setting -> game.allAssets.sett.region
    }

    // ---------------------------------------------------
    // Class
    // ---------------------------------------------------

    object Static {
        enum class TypeScreen {
            Menu, Rules, Setting
        }
    }

}