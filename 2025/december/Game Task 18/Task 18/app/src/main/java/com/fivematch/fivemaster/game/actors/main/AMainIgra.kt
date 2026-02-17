package com.fivematch.fivemaster.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.fivematch.fivemaster.game.actors.AToy
import com.fivematch.fivemaster.game.actors.button.AButton
import com.fivematch.fivemaster.game.screens.IgraScreen
import com.fivematch.fivemaster.game.screens.ResultScreen
import com.fivematch.fivemaster.game.utils.Block
import com.fivematch.fivemaster.game.utils.TIME_ANIM_SCREEN
import com.fivematch.fivemaster.game.utils.actor.animDelay
import com.fivematch.fivemaster.game.utils.actor.animHide
import com.fivematch.fivemaster.game.utils.actor.animShow
import com.fivematch.fivemaster.game.utils.actor.disable
import com.fivematch.fivemaster.game.utils.actor.enable
import com.fivematch.fivemaster.game.utils.actor.setOnClickListener
import com.fivematch.fivemaster.game.utils.advanced.AdvancedMainGroup
import com.fivematch.fivemaster.game.utils.gdxGame

class AMainIgra(override val screen: IgraScreen): AdvancedMainGroup() {

    private val imgPan  = Image(gdxGame.assetsAll.pusik)
    private val btnBack = AButton(screen, AButton.Type.Back)

    private val toys1   = List(5) { AToy(screen, gdxGame.assetsAll.listToy[0]) }
    private val toys2   = List(5) { AToy(screen, gdxGame.assetsAll.listToy[1]) }
    private val toys3   = List(5) { AToy(screen, gdxGame.assetsAll.listToy[2]) }
    private val toys4   = List(5) { AToy(screen, gdxGame.assetsAll.listToy[3]) }
    private val toys5   = List(5) { AToy(screen, gdxGame.assetsAll.listToy[4]) }
    private val toysAll = (toys1 + toys2 + toys3 + toys4 + toys5).shuffled()

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPan()
        addBtnBack()
        addToys()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPan() {
        addActor(imgPan)
        imgPan.setBounds(-99f, 94f, 1293f, 1293f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(80f, 1750f, 90f, 90f)
        btnBack.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addToys() {
        var nx = 147f
        var ny = 1013f

        var counter = 0

        var toy1: AToy? = null
        var toy2: AToy? = null

        var tmpActions1: Action? = null
        var tmpActions2: Action? = null

        toysAll.onEachIndexed { index, aToy ->
            addActor(aToy)
            aToy.setBounds(nx, ny, 160f, 160f)
            nx += 0f + 160f
            if (index.inc() % 5 == 0) {
                nx = 147f
                ny -= 16f + 160f
            }
            aToy.setOrigin(Align.center)
            aToy.setOnClickListener {
                counter++

                if (counter > 2) return@setOnClickListener

                gdxGame.soundUtil.apply { play(select) }

                aToy.disable()
                aToy.selected()

                if (counter == 1) {
                    toy1 = aToy
                } else {
                    toy2 = aToy

                    toy1!!.apply {
                        unselected()
                        toFront()
                        enable()
                    }
                    toy2.apply {
                        unselected()
                        toFront()
                        enable()
                    }

                    tmpActions1 = Actions.moveTo(toy2.x, toy2.y, 0.4f)
                    tmpActions2 = Actions.sequence(
                        Actions.moveTo(toy1.x, toy1.y, 0.4f),
                        Actions.run {
                            counter = 0

                            if (
                                toys1.all { it.y == toys1.first().y } &&
                                toys2.all { it.y == toys2.first().y } &&
                                toys3.all { it.y == toys3.first().y } &&
                                toys4.all { it.y == toys4.first().y } &&
                                toys5.all { it.y == toys5.first().y }
                            ) {
                                screen.hideScreen {
                                    gdxGame.navigationManager.navigate(ResultScreen::class.java.name, screen::class.java.name)
                                }
                            }
                        }
                    )

                    toy1.addAction(tmpActions1)
                    toy2.addAction(tmpActions2)

                }
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}