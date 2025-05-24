package com.plinkray.ioosudmarine.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinkray.ioosudmarine.game.actors.checkbox.ACheckBox
import com.plinkray.ioosudmarine.game.box2d.AbstractBody
import com.plinkray.ioosudmarine.game.box2d.BodyId
import com.plinkray.ioosudmarine.game.box2d.WorldUtil
import com.plinkray.ioosudmarine.game.box2d.bodies.BBall
import com.plinkray.ioosudmarine.game.box2d.bodies.BCircle
import com.plinkray.ioosudmarine.game.box2d.bodies.BFinishSensor
import com.plinkray.ioosudmarine.game.box2d.bodies.BLevel1
import com.plinkray.ioosudmarine.game.box2d.bodies.BLevel2
import com.plinkray.ioosudmarine.game.box2d.bodies.BLevel3
import com.plinkray.ioosudmarine.game.box2d.bodies.BPlayground
import com.plinkray.ioosudmarine.game.utils.*
import com.plinkray.ioosudmarine.game.utils.actor.animHide
import com.plinkray.ioosudmarine.game.utils.actor.animShow
import com.plinkray.ioosudmarine.game.utils.actor.disable
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedBox2dScreen
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedStage

class GameScreen3() : AdvancedBox2dScreen(WorldUtil()) {

    // Actor
    private val aClick   = Image(gdxGame.assetsAll.click)
    private val aPanel   = Actor()
    private val aBoxList = List(3) { ACheckBox(this, ACheckBox.Type.Ball) }

    // Body
    private val bPlayground   = BPlayground(this)
    private val bLevel1       = BLevel3(this)
    //private val bList56Circle = List(56) { BCircle(this) }
    //private val bList42Circle = List(42) { BCircle(this) }
    private val bBallList     = MutableList(3) { BBall(this) }

    override fun show() {
        stageUI.root.color.a = 0f
        stageUI.addAndFillActor(Image(gdxGame.assetsAll._3.region))
        super.show()

        stageUI.root.animShow(TIME_ANIM_SCREEN) {
            aClick.addAction(Actions.forever(Actions.sequence(
                Actions.alpha(0.35f, 0.37f),
                Actions.alpha(1f, 0.37f),
            )))
        }
    }

    override fun hideScreen(block: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN) { block.invoke() }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Playground()
        createB_Level1()
        //createB_ListCircle_56()
        //createB_ListCircle_42()
        createB_ListWin()
        createB_ListFail()
        createB_Ball()

        addClickImg()
        addBoxList()
        addPanel()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addClickImg() {
        addActor(aClick)
        aClick.setBounds(275f, 1697f, 529f, 72f)
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
                    bBallList.removeFirstOrNull()?.also { ball ->
                        ball.body?.apply {
                            setTransform(tmpVector.set(67f + x + 32.5f, 1678f + y).toB2, 0f)
                            gravityScale = 1f
                            applyForceToCenter(0f, 1f, true)
                        }
                        ball.isFirst.set(true)

                        gdxGame.soundUtil.apply { play(click) }
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

    private fun createB_Level1() {
        bLevel1.apply {
            id = BodyId.Game.STATIC
            collisionList.add(BodyId.Game.BALL)
        }

        bLevel1.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

//    private fun createB_ListCircle_56() {
//        var nx = 118f
//        var ny = 1588f
//        bList56Circle.onEachIndexed { index, bCircle ->
//            bCircle.apply {
//                id = BodyId.Game.STATIC
//                collisionList.add(BodyId.Game.BALL)
//            }
//
//            bCircle.create(nx, ny, 34f, 34f)
//            nx += 101 + 34
//            if (index.inc() % 7 == 0) {
//                nx = 118f
//                ny -= 147 + 34
//            }
//        }
//    }
//
//    private fun createB_ListCircle_42() {
//        var nx = 185f
//        var ny = 1498f
//        bList42Circle.onEachIndexed { index, bCircle ->
//            bCircle.apply {
//                id = BodyId.Game.STATIC
//                collisionList.add(BodyId.Game.BALL)
//            }
//
//            bCircle.create(nx, ny, 34f, 34f)
//            nx += 102 + 34
//            if (index.inc() % 6 == 0) {
//                nx = 185f
//                ny -= 147 + 34
//            }
//        }
//    }

    private fun createB_Ball() {
        bBallList.onEach { ball ->
            ball.id = BodyId.Game.BALL
            ball.collisionList.addAll(arrayOf(BodyId.Game.STATIC, BodyId.Game.WIN, BodyId.Game.FAIL))

            ball.create(2000f, 2000f, 55f, 55f)
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
                            gdxGame.soundUtil.apply { play(this.win) }

                            gdxGame.ds_Balance.update { it + 5 }

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
                            gdxGame.soundUtil.apply { play(this.win) }

                            gdxGame.ds_Balance.update { it - 5 }

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