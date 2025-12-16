package com.watermil.terwater.game.actors.main

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.watermil.terwater.game.actors.button.AButton
import com.watermil.terwater.game.screens.IgraScreen
import com.watermil.terwater.game.screens.ResultTryScreen
import com.watermil.terwater.game.screens.ResultWinScreen
import com.watermil.terwater.game.utils.Block
import com.watermil.terwater.game.utils.TIME_ANIM_SCREEN
import com.watermil.terwater.game.utils.actor.animDelay
import com.watermil.terwater.game.utils.actor.animHide
import com.watermil.terwater.game.utils.actor.animShow
import com.watermil.terwater.game.utils.actor.disable
import com.watermil.terwater.game.utils.actor.setBounds
import com.watermil.terwater.game.utils.actor.setOnClickListener
import com.watermil.terwater.game.utils.advanced.AdvancedGroup
import com.watermil.terwater.game.utils.advanced.AdvancedMainGroup
import com.watermil.terwater.game.utils.advanced.AdvancedScreen
import com.watermil.terwater.game.utils.gdxGame

class AMainIgra(override val screen: IgraScreen): AdvancedMainGroup() {

    private val imgFind  = Image(gdxGame.assetsAll.golde)
    private val imgPanel = Image(gdxGame.assetsAll.table)
    private val btnX     = AButton(screen, AButton.Type.X)

    class WinnerItem(
        override val screen: AdvancedScreen,
        val reg: TextureRegion,
        val isWin: Boolean,
    ): AdvancedGroup() {
        override fun addActorsOnGroup() {
            addAndFillActor(Image(reg))
        }
    }

    private val listReg     = gdxGame.assetsAll.listItem.shuffled()
    private val imgFindReg  = Image(listReg.first())
    private val listWi      = List(9) { WinnerItem(screen, listReg[it], it == 0) }.shuffled()

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgFind()
        addBtnX()
        addImgPan()

        addImgFinder()


        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgFind() {
        addActor(imgFind)
        imgFind.setBounds(378f, 1371f, 325f, 325f)
    }

    private fun addImgPan() {
        addActor(imgPanel)
        imgPanel.setBounds(115f, 410f, 850f, 850f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(76f, 1696f, 120f, 120f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgFinder() {
        addActor(imgFindReg)
        imgFindReg.setBounds(436f, 1430f, 207f, 207f)


        var newX = 160f
        var newY = 1055f

        listWi.onEachIndexed { index, data ->
            data.color.a = 0f
            addActor(data)
            data.setBounds(newX, newY, 160f, 160f)

            newX += 140f + 160f
            if (index.inc() % 3 == 0) {
                newX = 160f
                newY -= 140f + 160f
            }

            data.setOnClickListener(gdxGame.soundUtil) {
                data.animShow(TIME_ANIM_SCREEN) {
                    this.disable()
                    data.animDelay(0.5f) {
                        listWi.forEach { it.animShow(0.25f) }
                        data.animDelay(1f) {
                            if (data.isWin) gdxGame.navigationManager.navigate(ResultWinScreen::class.java.name)
                            else gdxGame.navigationManager.navigate(ResultTryScreen::class.java.name)
                        }
                    }
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