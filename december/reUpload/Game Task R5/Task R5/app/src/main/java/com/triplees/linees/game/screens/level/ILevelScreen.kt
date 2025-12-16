package com.triplees.linees.game.screens.level

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.triplees.linees.game.LibGDXGame
import com.triplees.linees.game.actors.ATimerGroup
import com.triplees.linees.game.actors.TmpGroup
import com.triplees.linees.game.actors.button.AButton
import com.triplees.linees.game.actors.checkbox.ACheckBox
import com.triplees.linees.game.screens.ResultScreen
import com.triplees.linees.game.utils.TIME_ANIM
import com.triplees.linees.game.utils.actor.animHide
import com.triplees.linees.game.utils.actor.animShow
import com.triplees.linees.game.utils.actor.setOnClickListener
import com.triplees.linees.game.utils.advanced.AdvancedScreen
import com.triplees.linees.game.utils.advanced.AdvancedStage
import com.triplees.linees.game.utils.region

abstract class ILevelScreen(final override val game: LibGDXGame, val level: Static.ELevel) : AdvancedScreen() {

    private val assets = game.allAssets

    protected val tmpGroup by lazy { TmpGroup(this) }

    private val panelImg = Image(getBackgroundByType())
    private val menuBtn  by lazy { AButton(this, AButton.Static.Type.MENU) }
    private val pauseBox by lazy { ACheckBox(this, ACheckBox.Static.Type.PAUSE) }
    private val timer    by lazy { ATimerGroup(this) }

    // Field
    protected val indexList = (0..5).shuffled()

    override fun show() {
        setBackBackground(game.loaderAssets.piramida.region)
        super.show()
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addMenu()
        addPause()
        addTimer()

        stageUI.root.animShow(TIME_ANIM) {
            addActorsOnStage()
            timer.startTimer {
                ResultScreen.isWin = false
                stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(ResultScreen::class.java.name) }
            }
        }
    }

    abstract fun AdvancedStage.addActorsOnStage()

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(104f, 516f, 867f, 911f)
    }

    private fun AdvancedStage.addMenu() {
        addActor(menuBtn)
        menuBtn.setBounds(43f, 1679f, 131f, 134f)
        menuBtn.setOnClickListener(game.soundUtil) { stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() } }
    }

    private fun AdvancedStage.addPause() {
        addActor(pauseBox)
        pauseBox.setBounds(917f, 1679f, 131f, 134f)
        pauseBox.setOnCheckListener { timer.isPause = it }
    }

    private fun AdvancedStage.addTimer() {
        addActor(timer)
        timer.setBounds(293f, 1663f, 505f, 166f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getBackgroundByType() = when(level) {
        Static.ELevel.L1 -> assets.horizontal
        Static.ELevel.L2 -> assets.vertical
    }

    protected fun stopTimer() {
        timer.isPause = true
    }

    // ---------------------------------------------------
    // class
    // ---------------------------------------------------

    object Static {
        enum class ELevel {
            L1, L2,
        }
    }

}