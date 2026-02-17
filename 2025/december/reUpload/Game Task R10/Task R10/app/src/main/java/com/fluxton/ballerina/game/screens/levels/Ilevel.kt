package com.fluxton.ballerina.game.screens.levels

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.fluxton.ballerina.game.LibGDXGame
import com.fluxton.ballerina.game.actors.button.AButton
import com.fluxton.ballerina.game.actors.checkbox.ACheckBox
import com.fluxton.ballerina.game.box2d.AbstractBody
import com.fluxton.ballerina.game.box2d.BodyId
import com.fluxton.ballerina.game.box2d.WorldUtil
import com.fluxton.ballerina.game.box2d.bodies.BBall
import com.fluxton.ballerina.game.box2d.bodiesGroup.BGBorders
import com.fluxton.ballerina.game.screens.IS_WIN
import com.fluxton.ballerina.game.screens.PinkMenuScreen
import com.fluxton.ballerina.game.screens.ResultScreen
import com.fluxton.ballerina.game.utils.HEIGHT_BOX2D
import com.fluxton.ballerina.game.utils.TIME_ANIM
import com.fluxton.ballerina.game.utils.WIDTH_BOX2D
import com.fluxton.ballerina.game.utils.actor.animHide
import com.fluxton.ballerina.game.utils.actor.setOnClickListener
import com.fluxton.ballerina.game.utils.advanced.AdvancedBox2dScreen
import com.fluxton.ballerina.game.utils.advanced.AdvancedStage
import com.fluxton.ballerina.game.utils.font.FontParameter
import com.fluxton.ballerina.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class Ilevel(final override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {

    private val fontP = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(64)
    private val font  = fontGenerator_KameronBold.generateFont(fontP)

    private val homeBtn  by lazy { AButton(this, AButton.Static.Type.HOME) }
    private val pauseBox by lazy { ACheckBox(this, ACheckBox.Static.Type.PAUSE) }
    private val panelImg    = Image(game.allAssets.panel)
    private val countLbl    = Label("", Label.LabelStyle(font, Color.WHITE))
    private val joystickImg = Image(game.allAssets.joystick)

    // BodyGroup
    private val bgBorders by lazy { BGBorders(this) }

    // Body
    abstract val bBall: BBall

    protected val flowCountStar = MutableStateFlow(0)

    final override fun AdvancedStage.addActorsOnStageUI() {
        addHomeBtn()
        addPauseBox()
        addPanelImg()
        addCountLbl()
        addJoystickImg()

        createBG_Borders()

        addActorsOnStage()

        bBall.beginContactBlockArray.add(AbstractBody.ContactBlock {
            when(it.id) {
                BodyId.BORDERS -> game.soundUtil.apply { play(BORDER) }
                BodyId.COIN -> {
                    game.soundUtil.apply { play(STAR) }
                    runGDX {
                        it.body?.setTransform(WIDTH_BOX2D*2, HEIGHT_BOX2D*2, 0f)
                        flowCountStar.value++

                        if (flowCountStar.value == 2) {
                            IS_WIN = true
                            stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(ResultScreen::class.java.name) }
                        }
                    }
                }
                BodyId.ENEMY -> {
                    runGDX {
                        flowCountStar.value = 0
                        IS_WIN = false
                        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(ResultScreen::class.java.name) }
                    }
                }
            }
        })
    }

    abstract fun AdvancedStage.addActorsOnStage()



    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addHomeBtn() {
        addActor(homeBtn)
        homeBtn.apply {
            setBounds(70f, 1736f, 156f, 165f)
            setOnClickListener { stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() } }
        }
    }

    private fun AdvancedStage.addPauseBox() {
        addActor(pauseBox)
        pauseBox.apply {
            setBounds(874f, 1736f, 156f, 165f)
            setOnCheckListener { isCheck -> isWorldPause = isCheck }
        }
    }

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.apply {
            setBounds(390f, 1771f, 300f, 107f)
        }
    }

    private fun AdvancedStage.addCountLbl() {
        addActor(countLbl)
        countLbl.apply {
            setBounds(480f, 1780f, 40f, 81f)
            setAlignment(Align.right)
        }

        coroutine?.launch {
            flowCountStar.collect { count ->
                runGDX { countLbl.setText(count) }
            }
        }
    }

    private fun AdvancedStage.addJoystickImg() {
        addActor(joystickImg)
        joystickImg.apply {
            setBounds(70f, 66f, 972f, 168f)
        }

        val left  = Actor()
        val right = Actor()
        val up    = Actor()
        addActors(left, right, up)
        left.apply {
            setBounds(70f, 70f, 156f, 164f)
            setOnClickListener(game.soundUtil) {
                bBall.body?.apply {
                    applyLinearImpulse(Vector2(-4f, 0f), worldCenter, true)
                }
            }
        }
        right.apply {
            setBounds(256f, 70f, 156f, 164f)
            setOnClickListener(game.soundUtil) {
                bBall.body?.apply {
                    applyLinearImpulse(Vector2(4f, 0f), worldCenter, true)
                }
            }
        }
        up.apply {
            setBounds(884f, 70f, 156f, 164f)
            setOnClickListener(game.soundUtil) {
                bBall.body?.apply {
                    applyLinearImpulse(Vector2(0f, 7f), worldCenter, true)
                }
            }
        }

    }

    // ---------------------------------------------------
    // create BodyGroup
    // ---------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH, HEIGHT)
    }


}