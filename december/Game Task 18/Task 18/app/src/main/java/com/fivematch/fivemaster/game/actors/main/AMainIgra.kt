package com.fivematch.fivemaster.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.fivematch.fivemaster.game.actors.AToy
import com.fivematch.fivemaster.game.actors.button.AButton
import com.fivematch.fivemaster.game.actors.checkbox.ACheckBox
import com.fivematch.fivemaster.game.screens.IgraScreen
import com.fivematch.fivemaster.game.screens.ResultScreen
import com.fivematch.fivemaster.game.screens.RulesScreen
import com.fivematch.fivemaster.game.utils.Block
import com.fivematch.fivemaster.game.utils.TIME_ANIM_SCREEN
import com.fivematch.fivemaster.game.utils.actor.animDelay
import com.fivematch.fivemaster.game.utils.actor.animHide
import com.fivematch.fivemaster.game.utils.actor.animShow
import com.fivematch.fivemaster.game.utils.actor.disable
import com.fivematch.fivemaster.game.utils.actor.enable
import com.fivematch.fivemaster.game.utils.actor.setOnClickListener
import com.fivematch.fivemaster.game.utils.advanced.AdvancedMainGroup
import com.fivematch.fivemaster.game.utils.advanced.AdvancedStage
import com.fivematch.fivemaster.game.utils.gdxGame
import com.fivematch.fivemaster.game.utils.runGDX
import kotlinx.coroutines.delay

class AMainIgra(override val screen: IgraScreen): AdvancedMainGroup() {

    private val imgText  = Image(gdxGame.assetsAll.pusik)
    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
    private val listImg  = AButton(screen, AButton.Type.Back)

    private val toys1   = List(4) { AToy(screen, gdxGame.assetsAll.listToy[0]) }
    private val toys2   = List(4) { AToy(screen, gdxGame.assetsAll.listToy[1]) }
    private val toys3   = List(4) { AToy(screen, gdxGame.assetsAll.listToy[2]) }
    private val toys4   = List(4) { AToy(screen, gdxGame.assetsAll.listToy[3]) }
    private val toys5   = List(4) { AToy(screen, gdxGame.assetsAll.listToy[4]) }
    private val toysAll = (toys1 + toys2 + toys3 + toys4 + toys5).shuffled()

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addImages()
        addMus()
        addToys()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgText)
        imgText.setBounds(154f, 264f, 773f, 1391f)
    }

    private fun addMus() {
        addActor(mus)
        mus.setBounds(426f, 1710f, 227f, 173f)
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) mus.check()
        mus.setOnCheckListener {
            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }
    }

    private fun addImages() {
        addActor(listImg)
        listImg.setBounds(294f, 36f, 491f, 173f)
        listImg.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addToys() {
        var nx = 181f
        var ny = 1378f

        var counter = 0

        var toy1: AToy? = null
        var toy2: AToy? = null

        var tmpActions1: Action? = null
        var tmpActions2: Action? = null

        toysAll.onEachIndexed { index, aToy ->
            addActor(aToy)
            aToy.setBounds(nx, ny, 170f, 170f)
            nx += 15f + 170f
            if (index.inc() % 4 == 0) {
                nx = 169f
                ny -= 103f + 170f
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
                    toy2!!.apply {
                        unselected()
                        toFront()
                        enable()
                    }

                    tmpActions1 = Actions.moveTo(toy2!!.x, toy2!!.y, 0.4f)
                    tmpActions2 = Actions.sequence(
                        Actions.moveTo(toy1!!.x, toy1!!.y, 0.4f),
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

                    toy1!!.addAction(tmpActions1)
                    toy2!!.addAction(tmpActions2)

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