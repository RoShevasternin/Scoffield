package com.relict.arthunt.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relict.arthunt.game.LibGDXGame
import com.relict.arthunt.game.actors.AItemPanel
import com.relict.arthunt.game.actors.ATimerPoe
import com.relict.arthunt.game.actors.button.AButton
import com.relict.arthunt.game.actors.checkbox.ACheckBox
import com.relict.arthunt.game.box2d.AbstractBody
import com.relict.arthunt.game.box2d.AbstractJoint
import com.relict.arthunt.game.box2d.BodyId
import com.relict.arthunt.game.box2d.bodies.BItem
import com.relict.arthunt.game.box2d.bodies.BOly
import com.relict.arthunt.game.box2d.bodies.standart.BStatic
import com.relict.arthunt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.relict.arthunt.game.utils.actor.animHide
import com.relict.arthunt.game.utils.actor.animShow
import com.relict.arthunt.game.utils.actor.setOnClickListener
import com.relict.arthunt.game.utils.advanced.AdvancedMouseScreen
import com.relict.arthunt.game.utils.advanced.AdvancedStage
import com.relict.arthunt.game.utils.region
import com.relict.arthunt.game.utils.runGDX
import com.relict.arthunt.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class OlyGameScreen(override val game: LibGDXGame) : AdvancedMouseScreen(game) {

    private val assets = game.allAssets

    // Actor
    private val aTimer         = ATimerPoe(this)
    private val aItemPanelList = List(3) { AItemPanel(this) }

    // Body
    private val bStatic = BStatic(this)
    private val bOly    = BOly(this)

    // Joint
    private val jPrismatic = AbstractJoint<PrismaticJoint, PrismaticJointDef>(this)

    // Field
    private val randomIndexList = (0..6).shuffled().take(3)
    private val randomCountList = List<Int>(3) { (3..4).random() }

    private val itemFlow = MutableSharedFlow<BItem>(replay = 15)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loadingAssets.BACKICH.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Static()
        createB_Oly()
        createJ_Prismatic()
        createB_Items()

        val lev = Image(when(OlyLevelScreen.LEVEL) {
            OlyLevelScreen.Static.Level.Easy -> game.allAssets.a
            OlyLevelScreen.Static.Level.Normal -> game.allAssets.b
            OlyLevelScreen.Static.Level.Hard -> game.allAssets.c
        })
        addActor(lev)
        lev.setBounds(298f, 1774f, 484f, 77f)

        val panel = Image(game.allAssets.fanera)
        addActor(panel)
        panel.setBounds(251f, 1468f, 562f, 291f)

        addBack()
        addPause()
        addTimer()
        addItemPanelList()

        aTimer.startTimer {
            aTimer.isPause = true
            isWorldPause   = true

            IS_WIN = false
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.navigationManager.navigate(OlyFinishScreen::class.java.name)
            }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        val menu = AButton(this@OlyGameScreen, AButton.Static.Type.MENU)
        addActor(menu)
        menu.setBounds(69f, 1702f, 159f, 160f)

        menu.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addPause() {
        val pause = ACheckBox(this@OlyGameScreen, ACheckBox.Static.Type.PAUSE)
        addActor(pause)
        pause.setBounds(843f, 1702f, 159f, 160f)

        pause.setOnCheckListener { isCheck ->
            aTimer.isPause = isCheck
            isWorldPause   = isCheck
        }
    }

    private fun AdvancedStage.addTimer() {
        addActor(aTimer)
        aTimer.setBounds(329f, 1609f, 405f, 144f)
    }

    private fun AdvancedStage.addItemPanelList() {
        var nx = 264f

        aItemPanelList.onEachIndexed { index, aItemPanel ->
            addActor(aItemPanel)
            aItemPanel.setBounds(nx, 1482f, 116f, 61f)
            nx += 88f + 116f

            aItemPanel.update(randomCountList[index], assets.items[randomIndexList[index]])
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        bStatic.create(-140f, -140f, 279f, 279f)
    }

    private fun createB_Oly() {
        bOly.create(283f, -11f, 515f, 491f)

        val itemId1 = BodyId.Game.items[randomIndexList[0]]
        val itemId2 = BodyId.Game.items[randomIndexList[1]]
        val itemId3 = BodyId.Game.items[randomIndexList[2]]

        bOly.collisionList.addAll(arrayOf(itemId1, itemId2, itemId3))

        bOly.beginContactBlockArray.add(AbstractBody.ContactBlock { bEnemy ->
            when (bEnemy.id) {
                itemId1 -> {
                    bEnemy as BItem
                    if (bEnemy.atomicBoolean.getAndSet(false)) {
                        itemFlow.tryEmit(bEnemy)
                        aItemPanelList[0].caught()
                        checkAllItemPanel()

                        game.soundUtil.apply { play(poimal) }
                    }
                }
                itemId2 -> {
                    bEnemy as BItem
                    if (bEnemy.atomicBoolean.getAndSet(false)) {
                        itemFlow.tryEmit(bEnemy)
                        aItemPanelList[1].caught()
                        checkAllItemPanel()

                        game.soundUtil.apply { play(poimal) }
                    }
                }
                itemId3 -> {
                    bEnemy as BItem
                    if (bEnemy.atomicBoolean.getAndSet(false)) {
                        itemFlow.tryEmit(bEnemy)
                        aItemPanelList[2].caught()
                        checkAllItemPanel()

                        game.soundUtil.apply { play(poimal) }
                    }
                }
            }
        })
    }

    private fun createB_Items() {
        repeat(15) {
            BItem(this).also { bItem ->
                bItem.collisionList.add(BodyId.Game.Oly)

                bItem.renderBlockArray.add(AbstractBody.RenderBlock {
                    bItem.body?.let {
                        if (it.position.y <= 0f) {
                            if (bItem.atomicBoolean.getAndSet(false)) {
                                itemFlow.tryEmit(bItem)
                            }
                        }
                    }
                })

                bItem.bodyDef.gravityScale = 0f
                bItem.create(-300f, 0f, 208f, 208f)

                itemFlow.tryEmit(bItem)
            }
        }

        coroutine?.launch {
            itemFlow.collect { bItem ->
                runGDX {
                    bItem.body?.apply {
                        bItem.setNoneId()

                        setLinearVelocity(0f, 0f)
                        gravityScale = 0f
                        isAwake = false

                        setTransform(Vector2(-300f, 0f).toB2, 0f)
                    }
                }
            }
        }
        coroutine?.launch {
            itemFlow.collect { bItem ->
                delay((500L..1000L).random())
                runGDX {
                    bItem.updateImage()
                    bItem.body?.apply {
                        setTransform((104..976).random().toFloat().toB2, 2024f.toB2, 0f)

                        gravityScale = 1f
                        isAwake      = true
                    }
                }
                delay(100)
                runGDX {
                    bItem.updateId()
                    bItem.atomicBoolean.set(true)
                }
            }
        }
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Prismatic() {
        jPrismatic.create(PrismaticJointDef().apply {
            bodyA = bStatic.body
            bodyB = bOly.body

            lowerTranslation = 0f
            upperTranslation = (1080f-514f).toB2
            enableLimit      = true
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun checkAllItemPanel() {
        if (aItemPanelList.all { it.counter == 0 }) {
            aTimer.isPause = true
            isWorldPause   = true

            IS_WIN = true
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.navigationManager.navigate(OlyFinishScreen::class.java.name)
            }
        }
    }

}