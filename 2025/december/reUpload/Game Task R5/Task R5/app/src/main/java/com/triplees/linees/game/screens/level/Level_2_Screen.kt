package com.triplees.linees.game.screens.level

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.triplees.linees.game.LibGDXGame
import com.triplees.linees.game.actors.AThing
import com.triplees.linees.game.screens.ResultScreen
import com.triplees.linees.game.utils.TIME_ANIM
import com.triplees.linees.game.utils.actor.animHide
import com.triplees.linees.game.utils.actor.disable
import com.triplees.linees.game.utils.actor.enable
import com.triplees.linees.game.utils.actor.setOnClickListener
import com.triplees.linees.game.utils.advanced.AdvancedGroup
import com.triplees.linees.game.utils.advanced.AdvancedStage
import com.triplees.linees.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Level_2_Screen(_game: LibGDXGame) : ILevelScreen(_game, Static.ELevel.L2) {

    private val things1  = List(4) { AThing(this, game.allAssets.list[indexList[0]]) }
    private val things2  = List(4) { AThing(this, game.allAssets.list[indexList[1]]) }
    private val things3  = List(4) { AThing(this, game.allAssets.list[indexList[2]]) }
    private val things4  = List(4) { AThing(this, game.allAssets.list[indexList[3]]) }
    private val thingsAll = (things1 + things2 + things3 + things4).shuffled()

    override fun AdvancedStage.addActorsOnStage() {
        runGDX {
            addActor(tmpGroup)
            tmpGroup.setBounds(213f, 684f, 624f, 636f)
        }
        coroutine?.launch(Dispatchers.Default) { tmpGroup.addThings() }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private suspend fun AdvancedGroup.addThings() {
        var nx = 0f
        var ny = 528f

        var counter = 0

        var thing1: AThing? = null
        var thing2: AThing? = null

        var tmpActions1: Action? = null
        var tmpActions2: Action? = null

        thingsAll.onEachIndexed { index, aThing ->
            runGDX {
                addActor(aThing)

                aThing.setBounds(nx, ny, 108f, 108f)
                ny -= 68f + 108f
                if (index.inc() % 4 == 0) {
                    ny = 528f
                    nx += 64f + 108f
                }
                aThing.setOrigin(Align.center)
                aThing.setOnClickListener {
                    counter++

                    if (counter > 2) return@setOnClickListener

                    game.soundUtil.apply { play(CLICK) }

                    aThing.disable()
                    aThing.selected()

                    if (counter == 1) {
                        thing1 = aThing
                    } else {
                        thing2 = aThing

                        thing1!!.apply {
                            unselected()
                            toFront()
                            enable()
                        }
                        thing2!!.apply {
                            unselected()
                            toFront()
                            enable()
                        }

                        tmpActions1 = Actions.moveTo(thing2!!.x, thing2!!.y, 0.3f)
                        tmpActions2 = Actions.sequence(
                            Actions.moveTo(thing1!!.x, thing1!!.y, 0.3f),
                            Actions.run {
                                counter = 0

                                if (
                                    things1.all { it.x == things1.first().x } &&
                                    things2.all { it.x == things2.first().x } &&
                                    things3.all { it.x == things3.first().x } &&
                                    things4.all { it.x == things4.first().x }
                                ) {
                                    stopTimer()
                                    ResultScreen.isWin = true
                                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(ResultScreen::class.java.name) }
                                }
                            }
                        )

                        thing1!!.addAction(tmpActions1)
                        thing2!!.addAction(tmpActions2)

                    }
                }
            }
            delay(90)
        }
    }

}