package com.filermax.detoxer.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.filermax.detoxer.MainActivity
import com.filermax.detoxer.game.actors.Back
import com.filermax.detoxer.game.actors.ControlPanel
import com.filermax.detoxer.game.actors.PanelBattery
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

class BatteryScreen: AdvancedScreen() {

    companion object {
        var isFinish = false
            private set
    }

    private var isStartClean = false

    private val descriptionTextStart  = "Optimizes battery life by turning off background tasks, lowering brightness, and closing apps. You can extend your battery life with just one click."
    private val descriptionTextFinish = "Battery life has been successfully optimized!"

    private val controlPanel     = ControlPanel(ControlPanel.Type.BATTERY)
    private val panelBoost       = PanelBattery(if (isFinish) PanelBattery.Type.FINISH else PanelBattery.Type.START)
    private val descriptionLabel = Label(if (isFinish) descriptionTextFinish else descriptionTextStart, ALabelStyle.style(SMMedium._26, GameColor.black_40))
    private val button           = AButton(AButtonStyle.btn)
    private val label            = Label("Start", ALabelStyle.style(SMSemiBold._33))
    private val back             = Back("Power Supply")



    override fun AdvancedStage.addActorsOnStageUI() {
        addControlPanel()
    }

    override fun AdvancedGroup.addActorsOnGroup() {
        addBack()

        coroutine.launch {
            withContext(Dispatchers.Default) {
                launch { addPanelBoost() }
                launch { addDescription() }
                if (isFinish.not()) launch { addButton() }
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
                onCheckClean   = {
                    if (isStartClean) isFinish = true
                    NavigationManager.navigate(CleanScreen(), BatteryScreen())
                }
                onCheckBoost   = {
                    if (isStartClean) isFinish = true
                    NavigationManager.navigate(BoostScreen(), BatteryScreen())
                }
                onCheckBattery = { }
                onCheckCooling = {
                    if (isStartClean) isFinish = true
                    NavigationManager.navigate(CoolingScreen(), BatteryScreen())
                }
            }
        }
    }

    private suspend fun AdvancedGroup.addPanelBoost() = suspendCoroutine<Unit> { continuation ->
        runGDX {
            addActor(panelBoost)
            panelBoost.apply {
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
            Actions.run { panelBoost.cleaning() }
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
            Actions.run { NavigationManager.navigate(BatteryScreen()) }
        ))
    }

}