//package com.fivematch.fivemaster.game.actors.main
//
//import com.badlogic.gdx.math.Vector2
//import com.badlogic.gdx.scenes.scene2d.Action
//import com.badlogic.gdx.scenes.scene2d.actions.Actions
//import com.badlogic.gdx.scenes.scene2d.ui.Image
//import com.badlogic.gdx.utils.Align
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//import lycky.fortune.tiger.game.LibGDXGame
//import lycky.fortune.tiger.game.actors.AResult
//import lycky.fortune.tiger.game.actors.ATimer
//import lycky.fortune.tiger.game.actors.AToy
//import lycky.fortune.tiger.game.actors.button.AButton
//import lycky.fortune.tiger.game.actors.checkbox.ACheckBox
//import lycky.fortune.tiger.game.utils.actor.animShow
//import lycky.fortune.tiger.game.utils.actor.animShow_Suspend
//import lycky.fortune.tiger.game.utils.actor.disable
//import lycky.fortune.tiger.game.utils.actor.enable
//import lycky.fortune.tiger.game.utils.actor.setOnClickListener
//import lycky.fortune.tiger.game.utils.advanced.AdvancedScreen
//import lycky.fortune.tiger.game.utils.advanced.AdvancedStage
//import lycky.fortune.tiger.game.utils.region
//import lycky.fortune.tiger.game.utils.runGDX
//import lycky.fortune.tiger.util.log
//import java.util.Vector
//
//class ManyToysScreen(override val game: LibGDXGame) : AdvancedScreen() {
//
//    private val toys1   = List(4) { AToy(this, game.gameAssets.toys[0]) }
//    private val toys2   = List(4) { AToy(this, game.gameAssets.toys[1]) }
//    private val toys3   = List(4) { AToy(this, game.gameAssets.toys[2]) }
//    private val toys4   = List(4) { AToy(this, game.gameAssets.toys[3]) }
//    private val toys5   = List(4) { AToy(this, game.gameAssets.toys[4]) }
//    private val toysAll = (toys1 + toys2 + toys3 + toys4 + toys5).shuffled()
//
//    // Actor
//    private val menu     = AButton(this, AButton.Static.Type.MENU)
//    private val pause    = ACheckBox(this, ACheckBox.Static.Type.PAUSE)
//    private val timer    = ATimer(this)
//    private val toyPanel = Image(game.gameAssets.TOY_PANEL).apply { color.a = 0f }
//    private val resultyw = AResult(this).apply { color.a = 0f }
//
//    override fun show() {
//        setBackgrounds(game.splashAssets.FIRST_BACKGROUND.region)
//        super.show()
//    }
//
//    override fun AdvancedStage.addActorsOnStageUI() {
//        coroutine?.launch {
//            runGDX {
//                addActor(menu)
//                menu.setBounds(16f, 1693f, 227f, 227f)
//                menu.setOnClickListener { game.navigationManager.back() }
//
//                addActor(pause)
//                pause.setBounds(837f, 1693f, 227f, 227f)
//                pause.setOnCheckListener { timer.isPause = it }
//
//                addTimer()
//                addToyPanel()
//            }
//            delay(400)
//            toyPanel.animShow_Suspend(0.3f)
//            addToys()
//
//            timer.startTimer {
//                addResult(false)
//            }
//        }
//    }
//
//    // ------------------------------------------------------------------------
//    // Add Actors
//    // ------------------------------------------------------------------------
//
//    private fun AdvancedStage.addTimer() {
//        addActor(timer)
//        timer.setBounds(325f, 1598f, 430f, 117f)
//    }
//
//    private fun AdvancedStage.addToyPanel() {
//        addActor(toyPanel)
//        toyPanel.setBounds(153f, 140f, 773f, 1391f)
//    }
//
//    private fun AdvancedStage.addResult(isgeeat: Boolean) {
//        addAndFillActor(resultyw)
//        resultyw.apply {
//            updateAndShow(isgeeat)
//            animShow(0.5f)
//        }
//    }
//
//    private suspend fun AdvancedStage.addToys() {
//        var nx = 169f
//        var ny = 1268f
//
//        var counter = 0
//
//        var toy1: AToy? = null
//        var toy2: AToy? = null
//
//        var tmpActions1: Action? = null
//        var tmpActions2: Action? = null
//
//        toysAll.onEachIndexed { index, aToy ->
//            runGDX {
//                addActor(aToy)
//                aToy.setBounds(nx, ny, 170f, 170f)
//                nx += 15f + 170f
//                if (index.inc() % 4 == 0) {
//                    nx = 169f
//                    ny -= 103f + 170f
//                }
//                aToy.setOrigin(Align.center)
//                aToy.setOnClickListener {
//                    counter++
//
//                    if (counter > 2) return@setOnClickListener
//
//                    game.soundUtil.apply { play(select) }
//
//                    aToy.disable()
//                    aToy.selected()
//
//                    if (counter == 1) {
//                        toy1 = aToy
//                    } else {
//                        toy2 = aToy
//
//                        toy1!!.apply {
//                            unselected()
//                            toFront()
//                            enable()
//                        }
//                        toy2!!.apply {
//                            unselected()
//                            toFront()
//                            enable()
//                        }
//
//                        tmpActions1 = Actions.moveTo(toy2!!.x, toy2!!.y, 0.4f)
//                        tmpActions2 = Actions.sequence(
//                            Actions.moveTo(toy1!!.x, toy1!!.y, 0.4f),
//                            Actions.run {
//                                counter = 0
//
//                                if (
//                                    toys1.all { it.y == toys1.first().y } &&
//                                    toys2.all { it.y == toys2.first().y } &&
//                                    toys3.all { it.y == toys3.first().y } &&
//                                    toys4.all { it.y == toys4.first().y } &&
//                                    toys5.all { it.y == toys5.first().y }
//                                ) {
//                                    addResult(true)
//                                }
//                            }
//                        )
//
//                        toy1!!.addAction(tmpActions1)
//                        toy2!!.addAction(tmpActions2)
//
//                    }
//                }
//            }
//            delay(80)
//        }
//    }
//
//
//}