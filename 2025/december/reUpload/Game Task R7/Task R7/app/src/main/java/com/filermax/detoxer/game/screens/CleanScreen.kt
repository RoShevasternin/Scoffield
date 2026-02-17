package com.filermax.detoxer.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.MainActivity
import com.filermax.detoxer.game.actors.Back
import com.filermax.detoxer.game.actors.ControlPanel
import com.filermax.detoxer.game.actors.PanelClean
import com.filermax.detoxer.game.actors.button.AButton
import com.filermax.detoxer.game.actors.button.AButtonStyle
import com.filermax.detoxer.game.actors.label.ALabelStyle
import com.filermax.detoxer.game.game
import com.filermax.detoxer.game.manager.NavigationManager
import com.filermax.detoxer.game.utils.GameColor
import com.filermax.detoxer.game.utils.actor.disable
import com.filermax.detoxer.game.utils.actor.enable
import com.filermax.detoxer.game.utils.actor.setBounds
import com.filermax.detoxer.game.utils.advanced.AdvancedGroup
import com.filermax.detoxer.game.utils.advanced.AdvancedScreen
import com.filermax.detoxer.game.utils.advanced.AdvancedStage
import com.filermax.detoxer.game.utils.hideTime
import com.filermax.detoxer.game.utils.runGDX
import com.filermax.detoxer.game.utils.showTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.filermax.detoxer.game.actors.label.ALabelStyle.Mulish.Medium as SMMedium
import com.filermax.detoxer.game.actors.label.ALabelStyle.Mulish.SemiBold as SMSemiBold
import com.filermax.detoxer.game.utils.Layout.Common as LC

class CleanScreen: AdvancedScreen() {

    companion object {
        var isFinish = false
            private set
    }

    private var isStartClean = false

    private val descriptionText = "We determine the volume of the system cache, residual files, advertising garbage, outdated APK files. You can remove all this with one click."

    private val controlPanel     = ControlPanel(ControlPanel.Type.CLEAN)
    private val panelClean       = PanelClean(if (isFinish) PanelClean.Type.FINISH else PanelClean.Type.START)
    private val descriptionLabel = Label(descriptionText, ALabelStyle.style(SMMedium._26, GameColor.black_40))
    private val button           = AButton(AButtonStyle.btn)
    private val label            = Label("Clean", ALabelStyle.style(SMSemiBold._33))
    private val back             = Back("System Cleaning")



    override fun AdvancedStage.addActorsOnStageUI() {
        addControlPanel()
    }

    override fun AdvancedGroup.addActorsOnGroup() {
        addBack()

        coroutine.launch {
            withContext(Dispatchers.Default) {
                launch { addPanelClean() }

                if (isFinish.not()) {
                    launch { addDescription() }
                    launch { addButton() }
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addControlPanel() {
        runGDX {
            addActor(controlPanel)
            controlPanel.apply {
                setBounds(LC.controlPanel)
                onCheckClean   = {  }
                onCheckBoost   = {
                    if (isStartClean) isFinish = true
                    NavigationManager.navigate(BoostScreen(), CleanScreen())
                }
                onCheckBattery = {
                    if (isStartClean) isFinish = true
                    NavigationManager.navigate(BatteryScreen(), CleanScreen())
                }
                onCheckCooling = {
                    if (isStartClean) isFinish = true
                    NavigationManager.navigate(CoolingScreen(), CleanScreen())
                }
            }
        }
    }

    private suspend fun AdvancedGroup.addPanelClean() = suspendCoroutine<Unit> { continuation ->
        runGDX {
            addActor(panelClean)
            panelClean.apply {
                setBounds(LC.screen)
                addAction(Actions.sequence(
                    Actions.alpha(0f),
                    Actions.fadeIn(showTime),
                    Actions.run { continuation.resume(Unit) }
                ))

                finishBlock = { finishCleaning() }
            }
        }
    }

    private suspend fun AdvancedGroup.addDescription() = suspendCoroutine<Unit> { continuation ->
        runGDX {
            addActor(descriptionLabel)
            descriptionLabel.apply {
                setBounds(LC.description)
                setAlignment(Align.center)
                wrap = true
                addAction(Actions.sequence(
                    Actions.alpha(0f),
                    Actions.fadeIn(showTime),
                    Actions.run { continuation.resume(Unit) }
                ))
            }
        }
    }

    private suspend fun AdvancedGroup.addButton() = suspendCoroutine<Unit> { continuation ->
        runGDX {
            addActor(button)

            button.apply {
                setBounds(LC.button)
                addAndFillActor(label)
                addAction(Actions.sequence(
                    Actions.alpha(0f),
                    Actions.fadeIn(showTime),
                    Actions.run { continuation.resume(Unit) }
                ))

                label.apply {
                    disable()
                    setAlignment(Align.center)
                }

                setOnClickListener { startCleaning() }
            }
        }
    }

    private fun AdvancedGroup.addBack() {
        addActor(back)
        back.apply {
            disable()
            setBounds(LC.back)
            addAction(Actions.alpha(0f))
            setOnClickListener { great() }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun startCleaning() {
        isStartClean = true
        button.disable()
        descriptionLabel.addAction(Actions.sequence(
            Actions.fadeOut(hideTime),
            Actions.run { panelClean.cleaning() }
        ))
    }

    private fun finishCleaning() {
        isStartClean = false
        isFinish = true
        controlPanel.enable()
        label.setText("Great")
        back.apply {
            enable()
            addAction(Actions.sequence(
                Actions.fadeIn(showTime),
            ))
        }
        button.apply {
            enable()
            setOnClickListener {
                disable()
                great()
            }
        }
    }

    private fun great() {
        mainGroup.addAction(Actions.sequence(
            Actions.fadeOut(hideTime),
            Actions.run { NavigationManager.navigate(CleanScreen()) }
        ))
    }

}