package com.dliw.drib.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.dliw.drib.game.LibGDXGame
import com.dliw.drib.game.actors.checkbox.ACheckBox
import com.dliw.drib.game.box2d.AbstractBody
import com.dliw.drib.game.box2d.AbstractBodyGroup
import com.dliw.drib.game.box2d.AbstractJoint
import com.dliw.drib.game.box2d.BodyId
import com.dliw.drib.game.box2d.WorldUtil
import com.dliw.drib.game.box2d.bodies.BBird
import com.dliw.drib.game.box2d.bodies.standart.BKinematic
import com.dliw.drib.game.box2d.bodiesGroup.BGBeam
import com.dliw.drib.game.box2d.bodiesGroup.BGBorders
import com.dliw.drib.game.box2d.destroyAll
import com.dliw.drib.game.utils.HEIGHT_UI
import com.dliw.drib.game.utils.TIME_ANIM
import com.dliw.drib.game.utils.WIDTH_UI
import com.dliw.drib.game.utils.actor.animHide
import com.dliw.drib.game.utils.actor.animShow
import com.dliw.drib.game.utils.actor.setOnClickListener
import com.dliw.drib.game.utils.advanced.AdvancedBox2dScreen
import com.dliw.drib.game.utils.advanced.AdvancedGroup
import com.dliw.drib.game.utils.advanced.AdvancedStage
import com.dliw.drib.game.utils.font.FontParameter
import com.dliw.drib.game.utils.region
import com.dliw.drib.game.utils.runGDX
import com.dliw.drib.game.utils.toB2
import com.dliw.drib.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class CountryScreen(override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {

    private val COUNT = 2

    private val fParameters = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(71)
    private val font71      = fontGenerator_GochiHandRegular.generateFont(fParameters)

    // Actor
    private val aPanelImg   = Image(game.allAssets.counter)
    private val aCounterLbl = Label("0", Label.LabelStyle(font71, Color.WHITE))
    private val aMenuImg    = Image(game.allAssets.menu)
    private val aPauseCBox  = ACheckBox(this, ACheckBox.Static.Type.PAUSE)

    // Body
    private val bBird = BBird(this)
    private val bKinematicList = List(COUNT) { BKinematic(this) }

    // BodyGroup
    private val bgBorders  = BGBorders(this)
    private val bgBeamList = List(COUNT) { BGBeam(this) }

    // Joint
    private val jWeldList = List(COUNT) { AbstractJoint<WeldJoint, WeldJointDef>(this) }

    // Field
    private val atomicBooleanList = List(COUNT) { AtomicBoolean(true) }
    private var counter = 0

    override fun show() {
        setBackBackground(game.startAssets.BACKGROUND.region)
        super.show()
    }

    override fun dispose() {
        bgBorders.destroy()
        bgBeamList.destroyAll()
        super.dispose()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createBG_Borders()
        createB_Bird()

        createBG_Beam()
        createB_Kinematic()
        createJ_Weld()

        addCounterLbls()
        addMenu()
        addPause()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        game.soundUtil.apply { play(swing_whoosh_weapon, volumeLevel*0.2f) }

        bBird.body?.applyLinearImpulse(Vector2(0f,
            when(SelectBirdScreen.indexBird) {
                0 -> 10f
                1 -> 12.5f
                2 -> 15f
                else -> 10f
            }
            ), bBird.body?.position, true)
        return super.touchDown(screenX, screenY, pointer, button)
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addCounterLbls() {
        addActors(aPanelImg, aCounterLbl)
        aPanelImg.setBounds(820f, 971f, 280f, 94f)
        aCounterLbl.apply {
            setBounds(905f, 972f, 110f, 84f)
            setAlignment(Align.center)
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(aMenuImg)
        aMenuImg.apply {
            setBounds(44f, 950f, 81f, 81f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
            }
        }
    }

    private fun AdvancedStage.addPause() {
        addActor(aPauseCBox)
        aPauseCBox.apply {
            setBounds(1722f, 950f, 81f, 81f)
            setOnCheckListener { isCheck ->
                isWorldPause = isCheck
            }
        }
    }

    // ---------------------------------------------------
    // create BodyGroup
    // ---------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

    private fun createBG_Beam() {
        bgBeamList.onEach { beam ->
            beam.create(1554f, -320f, 366f, 1720f)
        }
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Bird() {
        bBird.create(157f, 560f, 150f, 150f)

        bBird.beginContactBlockArray.add(AbstractBody.ContactBlock {
            when(it.id) {
                BodyId.BORDERS -> {
                    game.soundUtil.apply { play(fail, volumeLevel*0.4f) }

                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
                BodyId.Coyntry.WIN -> {
                    game.soundUtil.apply { play(win) }

                    counter++
                    aCounterLbl.setText(counter)
                }
            }
        })
    }

    private fun createB_Kinematic() {
        val impulse  = Vector2(100f, 100f)

        val tmpJoint        = AbstractJoint<WeldJoint, WeldJointDef>(this)
        val tmpWeldJointDef = WeldJointDef()

        bKinematicList.onEachIndexed { index, kinematic ->
            kinematic.create(1709f, 1024f, 56f, 56f)

            kinematic.renderBlockArray.add(AbstractBody.RenderBlock {
                if (kinematic.body!!.position.x < -2f) {
                    if (atomicBooleanList[index].getAndSet(false)) {
                        runGDX {
                            kinematic.body!!.setTransform(Vector2(2000f, (755..1300).random().toFloat()).toB2, 0f)

                            tmpJoint.apply {
                                create(tmpWeldJointDef.apply {
                                    bodyA = bKinematicList[index].body
                                    bodyB = bgBeamList[index].bBeamBottom.body

                                    localAnchorA.set(Vector2(28f, 28f).toB2.sub((bodyA.userData as AbstractBody).center))
                                    localAnchorB.set(Vector2(183f, 1376f).toB2.sub((bodyB.userData as AbstractBody).center))
                                })
                            }

                            coroutine?.launch {
                                delay(300)
                                runGDX {
                                    tmpJoint.destroy()
                                    atomicBooleanList[index].set(true)

                                    bgBeamList[index].bBeamBottom.body?.apply {
                                        applyLinearImpulse(impulse, worldCenter, false)
                                    }
                                }
                            }
                        }
                    }
                }
            })
        }

        coroutine?.launch {
            bKinematicList.onEach { kinematic ->
                runGDX { kinematic.body?.setLinearVelocity(-2f, 0f) }
                delay(7000)
            }
        }
    }

    // ---------------------------------------------------
    // create Joint
    // ---------------------------------------------------

    private fun createJ_Weld() {
        jWeldList.onEachIndexed { index, joint ->
            joint.create(WeldJointDef().apply {
                bodyA = bKinematicList[index].body
                bodyB = bgBeamList[index].bBeamTop.body

                localAnchorA.set(Vector2(28f, 28f).toB2.sub((bodyA.userData as AbstractBody).center))
                localAnchorB.set(Vector2(183f, 352f).toB2.sub((bodyB.userData as AbstractBody).center))
            })
        }
    }

}