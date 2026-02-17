package com.obezana.playtocrypto.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.obezana.playtocrypto.game.actors.Gomno1_ili_Gomno2
import com.obezana.playtocrypto.game.actors.OyOy_A_Kto_Tuta_Nakakala_A
import com.obezana.playtocrypto.game.actors.button.AButton
import com.obezana.playtocrypto.game.actors.button.AButtonStyle
import com.obezana.playtocrypto.game.actors.checkbox.ACheckBox
import com.obezana.playtocrypto.game.actors.checkbox.ACheckBoxStyle
import com.obezana.playtocrypto.game.actors.image.AImage
import com.obezana.playtocrypto.game.box2d.WorldUtil
import com.obezana.playtocrypto.game.box2d.bodies.BCoin
import com.obezana.playtocrypto.game.manager.FontTTFManager
import com.obezana.playtocrypto.game.manager.NavigationManager
import com.obezana.playtocrypto.game.manager.SpriteManager
import com.obezana.playtocrypto.game.utils.GameColor
import com.obezana.playtocrypto.game.utils.actor.animHide
import com.obezana.playtocrypto.game.utils.actor.animShow
import com.obezana.playtocrypto.game.utils.actor.disable
import com.obezana.playtocrypto.game.utils.actor.setOnClickListener
import com.obezana.playtocrypto.game.utils.advanced.AdvancedBox2dScreen
import com.obezana.playtocrypto.game.utils.advanced.AdvancedStage
import com.obezana.playtocrypto.game.utils.runGDX
import com.obezana.playtocrypto.game.utils.toB2
import com.obezana.playtocrypto.util.log
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

class LevOneScreen: AdvancedBox2dScreen(WorldUtil()) {

    private val randomLevel = Random.nextBoolean()

    // Actor
    private val sharList      = List(4) { AImage(SpriteManager.GameRegion.pufik.region) }
    private val zelenkaImg    = Image(SpriteManager.GameRegion.zelenka.region)
    private val zelenkaLbl    = Label("", Label.LabelStyle(FontTTFManager.Regularka.font_39.font, Color.WHITE))
    private val clickScoreLbl = Label("", Label.LabelStyle(FontTTFManager.Regularka.font_21.font, GameColor.black))
    private val pauseBtn      = ACheckBox(ACheckBoxStyle.pause)
    private val esurtBtn      = Image(SpriteManager.GameRegion.ui_spin.region)
    private val daEbalVRotCodNoNeSilnoBoLublu = getFrontik()

    // Body
    private val bCoinList = List(19) { BCoin(this, it) }

    // Fields
    private val sharFlow    = MutableSharedFlow<AImage>(replay = 4)
    private val coinFlow    = MutableSharedFlow<BCoin>(replay = 19)
    private val zelenkaFlow = MutableStateFlow(0)

    private val sharListDelayTime = longArrayOf(4000, 5000, 6000, 7000)
    private val coinListDelayTime = longArrayOf(300, 400, 500, 600, 750, 820, 900, 950, 1000)
    private val scoreList         = intArrayOf(100, 200, 250, 300, 325, 333, 410, 580, 600, 700, 800, 999)

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(getBackground())
        super.show()
        stageUI.root.animShow(0.5f)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine.launch {
            runGDX {
                mainGroup.disable()
                addPufikList()
                addZelenkaLbl()
                addPauseBtn()
                addAndFillActor(daEbalVRotCodNoNeSilnoBoLublu)

                createB_CoinList()
                addScoreLbl()

            }
            collectZelenkaLbl()
            startPufic()
            startCoin()

        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPufikList() {
        fun getRandomTime() = (3..10).random() / 10f

        sharList.onEach {
            addActor(it)
            it.setBounds(-185f, 0f, 185f, 238f)
            it.setOrigin(Align.center)
            val randomScale = (4..10).random() / 10f
            it.addAction(Actions.scaleTo(randomScale, randomScale))

            it.image.addAction(Actions.forever(Actions.sequence(
                Actions.delay(getRandomTime()),
                Actions.moveBy(0f, 20f, getRandomTime()),
                Actions.moveBy(0f, -40f, getRandomTime()),
                Actions.moveBy(0f, 20f, getRandomTime()),
            )))
        }
    }

    private fun AdvancedStage.addZelenkaLbl() {
        addActors(zelenkaImg, zelenkaLbl)
        zelenkaImg.setBounds(26f, 693f, 275f, 71f)
        zelenkaLbl.apply {
            setBounds(49f, 706f, 230f, 47f)
            setAlignment(Align.center)
        }
    }

    private fun AdvancedStage.addScoreLbl() {
        addActor(clickScoreLbl)
        clickScoreLbl.apply {
            setSize(70f, 25f)
            setAlignment(Align.center)
            animHide()
        }
    }

    private fun AdvancedStage.addPauseBtn() {
        addActors(pauseBtn, esurtBtn)
        pauseBtn.apply {
            setBounds(1422f, 670f, 93f, 94f)
            setOnCheckListener { isPause = it }
        }
        esurtBtn.apply {
            setBounds(1422f, 569f, 93f, 94f)
            setOnClickListener { NavigationManager.navigate(LevOneScreen(), YrovniScreen()) }
        }
    }


    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_CoinList() {
        bCoinList.onEach { coin ->
            coin.bodyDef.gravityScale = 0f
            coin.bodyDef.linearDamping = (4..9).random() / 10f
            coin.create(0f, 800f, 90f, 90f)

            val isRestartCoin = AtomicBoolean(false)

            coin.renderBlock = {
                if (coin.body?.position?.y!! <= 0) if (isRestartCoin.getAndSet(true).not()) coin.restartCoin(isRestartCoin)
            }

            coin.actor?.setOnClickListener {
                runGDX {
                    scoreList.random().also { score ->
                        clickScoreLbl.apply {
                            setText("+$score")
                            setPosition(it.x - 5, it.y + 66)
                            clearActions()
                            addAction(Actions.sequence(
                                Actions.fadeIn(0.2f),
                                Actions.parallel(
                                    Actions.moveBy(0f, 20f, 0.4f),
                                    Actions.fadeOut(0.4f)
                                )
                            ))
                        }

                        coin.restartCoin(isRestartCoin)
                        zelenkaFlow.value += score
                    }
                }
            }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getBackground() = if (randomLevel) SpriteManager.GameRegion.GREENCASTLE_BACKGROUND.region
    else SpriteManager.GameRegion.PURPLEHUETA_BACKGROUND.region
    private fun getFrontik() = if (randomLevel) Gomno1_ili_Gomno2() else OyOy_A_Kto_Tuta_Nakakala_A()

    private fun startPufic() {
        sharList.onEach { sharFlow.tryEmit(it) }

        coroutine.launch {
            sharFlow.collect { shar ->
                fun getRandomY()    = (0..800).random().toFloat()
                fun getRandomTime() = (10..15).random().toFloat()

                runGDX {
                    shar.apply {
                        clearActions()
                        setPosition(-185f, 0f)
                        y = getRandomY()
                        addAction(Actions.sequence(
                            Actions.moveTo(850f, getRandomY(), getRandomTime()),
                            Actions.moveTo(1555f, getRandomY(), getRandomTime()),
                            Actions.run { sharFlow.tryEmit(this) }
                        ))
                    }
                }
                delay(sharListDelayTime.random())
            }
        }
    }

    private fun startCoin() {
        fun getRandomX() = (0..1465).random().toFloat()
        bCoinList.onEach { coinFlow.tryEmit(it) }

        coroutine.launch {
            coinFlow.collect { coin ->
                runGDX {
                    coin.body?.setTransform(Vector2(getRandomX(), 800f).toB2, 0f)
                    coin.body?.gravityScale = 1f
                    coin.body?.isAwake = true
                }
                delay(coinListDelayTime.random())
            }
        }

    }

    private fun BCoin.restartCoin(isRestartCoin: AtomicBoolean) {
        runGDX {
            body?.isAwake = false
            body?.gravityScale = 0f
            body?.linearVelocity?.set(0f, 0f)
            body?.setTransform(Vector2(0f, 850f).toB2, 0f)

            coinFlow.tryEmit(this)

            isRestartCoin.set(false)
        }
    }

    private fun collectZelenkaLbl() {
        coroutine.launch { zelenkaFlow.collect { score ->  runGDX { zelenkaLbl.setText("$score") } } }
    }

}