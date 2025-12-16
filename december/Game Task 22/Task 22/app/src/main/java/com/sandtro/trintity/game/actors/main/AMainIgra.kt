package com.sandtro.trintity.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.sandtro.trintity.game.actors.ATimer
import com.sandtro.trintity.game.actors.AToy
import com.sandtro.trintity.game.actors.button.AButton
import com.sandtro.trintity.game.screens.IgraScreen
import com.sandtro.trintity.game.screens.ResultTryScreen
import com.sandtro.trintity.game.screens.ResultWinScreen
import com.sandtro.trintity.game.utils.Block
import com.sandtro.trintity.game.utils.TIME_ANIM_SCREEN
import com.sandtro.trintity.game.utils.actor.*
import com.sandtro.trintity.game.utils.advanced.AdvancedMainGroup
import com.sandtro.trintity.game.utils.gdxGame

class AMainIgra(override val screen: IgraScreen): AdvancedMainGroup() {

    private val imgPan  = Image(gdxGame.assetsAll.CIRCULCERA)
    private val btnBack = AButton(screen, AButton.Type.X)

    private val toys1   = List(5) { AToy(screen, gdxGame.assetsAll.listItem[0]) }
    private val toys2   = List(5) { AToy(screen, gdxGame.assetsAll.listItem[1]) }
    private val toys3   = List(5) { AToy(screen, gdxGame.assetsAll.listItem[2]) }
    private val toys4   = List(5) { AToy(screen, gdxGame.assetsAll.listItem[3]) }
    private val toys5   = List(5) { AToy(screen, gdxGame.assetsAll.listItem[4]) }
    private val toysAll = (toys1 + toys2 + toys3 + toys4 + toys5).shuffled()

    private val aTimer = ATimer(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPan()
        addBtnBack()
        addToys()
        addATimer()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(285f, 1447f, 511f, 238f)

        aTimer.start()

        aTimer.timeOut = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultTryScreen::class.java.name)
            }
        }
    }

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
                                    gdxGame.navigationManager.navigate(ResultWinScreen::class.java.name)
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