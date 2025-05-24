package com.plinko.aviator.slot.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinko.aviator.slot.game.LibGDXGame
import com.plinko.aviator.slot.game.actors.box.ACheckBox
import com.plinko.aviator.slot.game.box2d.AbstractBody
import com.plinko.aviator.slot.game.box2d.BodyId
import com.plinko.aviator.slot.game.box2d.WorldUtil
import com.plinko.aviator.slot.game.box2d.bodies.BBall
import com.plinko.aviator.slot.game.box2d.bodies.BCircle
import com.plinko.aviator.slot.game.box2d.bodies.BFinishSensor
import com.plinko.aviator.slot.game.box2d.bodies.BPlayground
import com.plinko.aviator.slot.game.utils.HEIGHT_UI
import com.plinko.aviator.slot.game.utils.TIME_ANIM_ALPHA
import com.plinko.aviator.slot.game.utils.WIDTH_UI
import com.plinko.aviator.slot.game.utils.actor.animHide
import com.plinko.aviator.slot.game.utils.actor.animShow
import com.plinko.aviator.slot.game.utils.actor.disable
import com.plinko.aviator.slot.game.utils.advanced.AdvancedBox2dScreen
import com.plinko.aviator.slot.game.utils.advanced.AdvancedStage
import com.plinko.aviator.slot.game.utils.region
import com.plinko.aviator.slot.game.utils.runGDX
import com.plinko.aviator.slot.game.utils.toB2
import java.util.concurrent.atomic.AtomicBoolean

class GameScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    // Actor
    private val aClick   = Image(game.allAssets.click)
    private val aPanel   = Actor()
    private val aBoxList = List(3) { ACheckBox(this, ACheckBox.Static.Type.BALL) }

    // Body
    private val bPlayground   = BPlayground(this)
    private val bList56Circle = List(56) { BCircle(this) }
    private val bList42Circle = List(42) { BCircle(this) }
    private val bBallList     = MutableList(3) { BBall(this) }

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.allAssets.PLINKO_MAIN.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_ALPHA) {
            aClick.addAction(Actions.forever(Actions.sequence(
                Actions.alpha(0.35f, 0.37f),
                Actions.alpha(1f, 0.37f),
            )))
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addClickImg()
        addBoxList()

        createB_Playground()
        createB_ListCircle_56()
        createB_ListCircle_42()
        createB_ListWin()
        createB_ListFail()
        createB_Ball()

        addPanel()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addClickImg() {
        addActor(aClick)
        aClick.setBounds(476f, 1694f, 133f, 70f)
        aClick.disable()
    }

    private fun AdvancedStage.addBoxList() {
        var nx = 790f
        aBoxList.onEach { box ->
            addActor(box)
            box.setBounds(nx, 1831f, 65f, 65f)
            nx += 16+65
            box.touchable = Touchable.disabled
        }
    }

    private fun AdvancedStage.addPanel() {
        val tmpVector = Vector2()
        var isFirst   = true

        addActor(aPanel)
        aPanel.setBounds(67f, 1678f, 950f, 128f)
        aPanel.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?,x: Float,y: Float,pointer: Int,button: Int): Boolean {
                return true
            }
            override fun touchUp( event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                if (isFirst) {
                    isFirst = false
                    aClick.addAction(Actions.sequence(Actions.removeActor()))
                }
                runGDX {
                    bBallList.removeFirst().also { ball ->
                        ball.body?.apply {
                            setTransform(tmpVector.set(67f + x + 32.5f, 1678f + y).toB2, 0f)
                            gravityScale = 1f
                            applyForceToCenter(0f, 1f, true)
                        }
                        ball.isFirst.set(true)

                        game.soundUtil.apply { play(sound_coin, volumeLevel * 0.2f) }
                    }

                    aBoxList.onEach { it.check() }
                    aBoxList.takeLast(bBallList.size).onEach { it.uncheck() }
                }
            }
        })
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------
    private fun createB_Playground() {
        bPlayground.apply {
            id = BodyId.Game.STATIC
            collisionList.add(BodyId.Game.BALL)
        }

        bPlayground.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

    private fun createB_ListCircle_56() {
        var nx = 118f
        var ny = 1588f
        bList56Circle.onEachIndexed { index, bCircle ->
            bCircle.apply {
                id = BodyId.Game.STATIC
                collisionList.add(BodyId.Game.BALL)
            }

            bCircle.create(nx, ny, 34f, 34f)
            nx += 101 + 34
            if (index.inc() % 7 == 0) {
                nx = 118f
                ny -= 147 + 34
            }
        }
    }

    private fun createB_ListCircle_42() {
        var nx = 185f
        var ny = 1498f
        bList42Circle.onEachIndexed { index, bCircle ->
            bCircle.apply {
                id = BodyId.Game.STATIC
                collisionList.add(BodyId.Game.BALL)
            }

            bCircle.create(nx, ny, 34f, 34f)
            nx += 102 + 34
            if (index.inc() % 6 == 0) {
                nx = 185f
                ny -= 147 + 34
            }
        }
    }

    private fun createB_Ball() {
        bBallList.onEach { ball ->
            ball.id = BodyId.Game.BALL
            ball.collisionList.addAll(arrayOf(BodyId.Game.STATIC, BodyId.Game.WIN, BodyId.Game.FAIL))

            ball.create(2000f, 2000f, 65f, 65f)
            ball.body?.apply {
                gravityScale = 0f
                isAwake = false
                setLinearVelocity(0f, 0f)
            }
        }
    }

    private fun createB_ListWin() {
        var nx = 217f

        List(3) { BFinishSensor(this) }.onEach { win ->
            win.id = BodyId.Game.WIN
            win.collisionList.add(BodyId.Game.BALL)

            win.create(nx, 117f, 98f, 98f)
            nx += 176+98

            win.beginContactBlockArray.add(AbstractBody.ContactBlock { enemy ->
                if (enemy.id == BodyId.Game.BALL) {
                    enemy as BBall
                    if (enemy.isFirst.getAndSet(false)) {
                        runGDX {
                            game.soundUtil.apply { play(sound_result, volumeLevel * 0.7f) }

                            enemy.body?.apply {
                                gravityScale = 0f
                                isAwake = false
                                setLinearVelocity(0f, 0f)
                                setTransform(100f, 100f, 0f)
                                bBallList.add(enemy)

                                aBoxList.onEach { it.check() }
                                aBoxList.takeLast(bBallList.size).onEach { it.uncheck() }
                            }
                        }
                    }
                }
            })
        }
    }

    private fun createB_ListFail() {
        var nx = 80f

        List(4) { BFinishSensor(this) }.onEach { win ->
            win.id = BodyId.Game.WIN
            win.collisionList.add(BodyId.Game.BALL)

            win.create(nx, 117f, 98f, 98f)
            nx += 176+98

            win.beginContactBlockArray.add(AbstractBody.ContactBlock { enemy ->
                if (enemy.id == BodyId.Game.BALL) {
                    enemy as BBall
                    if (enemy.isFirst.getAndSet(false)) {
                        runGDX {
                            game.soundUtil.apply { play(sound_nowin, volumeLevel * 0.7f) }

                            enemy.body?.apply {
                                gravityScale = 0f
                                isAwake = false
                                setLinearVelocity(0f, 0f)
                                setTransform(100f, 100f, 0f)
                                bBallList.add(enemy)

                                aBoxList.onEach { it.check() }
                                aBoxList.takeLast(bBallList.size).onEach { it.uncheck() }
                            }
                        }
                    }
                }
            })
        }
    }

}