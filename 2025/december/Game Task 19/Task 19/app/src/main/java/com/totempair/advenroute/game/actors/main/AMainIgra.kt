package com.totempair.advenroute.game.actors.main

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.totempair.advenroute.game.actors.ATimer
import com.totempair.advenroute.game.actors.OpenerCloser
import com.totempair.advenroute.game.actors.button.AButton
import com.totempair.advenroute.game.screens.IgraScreen
import com.totempair.advenroute.game.screens.ResultTryScreen
import com.totempair.advenroute.game.screens.ResultWinScreen
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.TIME_ANIM_SCREEN
import com.totempair.advenroute.game.utils.actor.animDelay
import com.totempair.advenroute.game.utils.actor.animHide
import com.totempair.advenroute.game.utils.actor.animShow
import com.totempair.advenroute.game.utils.actor.disable
import com.totempair.advenroute.game.utils.actor.enable
import com.totempair.advenroute.game.utils.actor.setOnClickListener
import com.totempair.advenroute.game.utils.advanced.AdvancedMainGroup
import com.totempair.advenroute.game.utils.gdxGame

class AMainIgra(override val screen: IgraScreen): AdvancedMainGroup() {

    private val imgFind  = Image(gdxGame.assetsAll.find)
    private val imgPanel = Image(gdxGame.assetsAll.pan)
    private val btnX     = AButton(screen, AButton.Type.X)
    private val aTimer   = ATimer(screen)



    data class Data(
        val id    : Int,
        val region: TextureRegion,
    )

    private val list12   by lazy { List(12) { index -> Data(index.inc(), gdxGame.assetsAll.listItem[index]) } }
    private val dataList by lazy { list12 + list12 }

    private var firstOpenClose : OpenerCloser? = null
    private var secondOpenClose: OpenerCloser? = null

    private var firstData : Data?    = null
    private var secondData: Data?    = null

    private var countPair = 0

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgFind()
        addBtnX()
        addImgPan()
        addATimer()
        addItems()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgFind() {
        addActor(imgFind)
        imgFind.setBounds(0f, 1557f, 1080f, 153f)
    }

    private fun addImgPan() {
        addActor(imgPanel)
        imgPanel.setBounds(378f, 141f, 324f, 120f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(63f, 1764f, 100f, 100f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(511f, 168f, 58f, 64f)
        aTimer.start()

        aTimer.timeOut = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultTryScreen::class.java.name)
            }
        }
    }

    private fun addItems() {
        when(GLOBAL_INDEX) {
            0 -> {
                addItem1()
            }
            1 -> {
                addItem2()
            }
            2 -> {
                addItem3()
            }
        }
    }

    private fun addItem1() {
        var newX = 0f
        var newY = 0f

        val listPos = listOf(
            Vector2(208f, 1218f),
            Vector2(349f, 1218f),
            Vector2(607f, 1218f),
            Vector2(748f, 1218f),
            Vector2(125f, 1069f),
            Vector2(266f, 1069f),
            Vector2(407f, 1069f),
            Vector2(548f, 1069f),
            Vector2(689f, 1069f),
            Vector2(830f, 1069f),
            Vector2(196f, 920f),
            Vector2(196+16f+125, 920f),

            Vector2(196+16*2f+125f*2, 920f),
            Vector2(196+16*3f+125f*3f, 920f),
            Vector2(196+16*4f+125f*4f, 920f),
            Vector2(266f, 771f),
            Vector2(266+16f+125, 771f),
            Vector2(266+16*2f+125f*2, 771f),
            Vector2(266+16*3f+125f*3f, 771f),
            Vector2(337f, 622f),
            Vector2(337+16f+125, 622f),
            Vector2(337+16*2f+125f*2, 622f),
            Vector2(408f, 473f),
            Vector2(408+16f+125, 473f),
        )

        dataList.shuffled().onEachIndexed { index, data ->
            OpenerCloser(screen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                newX = listPos[index].x
                newY = listPos[index].y

                item.setBounds(newX, newY, 125f, 125f)

                item.setOnClickListener(gdxGame.soundUtil) {
                    item.disable()
                    item.open {
                        if (firstOpenClose == null) {
                            firstOpenClose = item
                            firstData      = data
                        } else {
                            this.disable()
                            secondOpenClose = item
                            secondData      = data

                            if (firstData?.id != secondData?.id) {
                                this.clearActions()

                                // fail
                                gdxGame.soundUtil.apply { play(error) }

                                animDelay(0.4f) {
                                    firstOpenClose?.enable()
                                    secondOpenClose?.enable()

                                    firstOpenClose?.close()
                                    secondOpenClose?.close {
                                        firstOpenClose  = null
                                        secondOpenClose = null
                                        this.enable()
                                    }
                                }
                            } else {
                                this.clearActions()

                                // win
                                gdxGame.soundUtil.apply { play(win) }

                                animDelay(0.4f) {
                                    this.enable()

                                    firstOpenClose  = null
                                    secondOpenClose = null

                                    if (++countPair == 12) gdxGame.navigationManager.navigate(ResultWinScreen::class.java.name)
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun addItem2() {
        var newX = 0f
        var newY = 0f

        val listPos = listOf(
            Vector2(477f, 1263f),
            Vector2(408f, 1116f),
            Vector2(408f+16f+125f, 1116f),
            Vector2(338f, 970f),
            Vector2(338f+16f+125f, 970f),
            Vector2(338f+16f*2f+125f*2f, 970f),
            Vector2(199f, 824f),
            Vector2(199f+16f+125f, 824f),
            Vector2(199f+16f*2f+125f*2f, 824f),
            Vector2(199f+16f*3f+125f*3f, 824f),
            Vector2(199f+16f*4f+125f*4f, 824f),
            Vector2(124f, 678f),

            Vector2(124f+16f+125f, 678f),
            Vector2(124f+16f*2f+125f*2f, 678f),
            Vector2(124f+16f*3f+125f*3f, 678f),
            Vector2(124f+16f*4f+125f*4f, 678f),
            Vector2(124f+16f*5f+125f*5f, 678f),
            Vector2(60f, 531f),
            Vector2(60f+16f+125f, 531f),
            Vector2(60f+16f*2f+125f*2f, 531f),
            Vector2(60f+16f*3f+125f*3f, 531f),
            Vector2(60f+16f*4f+125f*4f, 531f),
            Vector2(60f+16f*5f+125f*5f, 531f),
            Vector2(60f+16f*6f+125f*6f, 531f),
        )

        dataList.shuffled().onEachIndexed { index, data ->
            OpenerCloser(screen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                newX = listPos[index].x
                newY = listPos[index].y

                item.setBounds(newX, newY, 125f, 125f)

                item.setOnClickListener(gdxGame.soundUtil) {
                    item.disable()
                    item.open {
                        if (firstOpenClose == null) {
                            firstOpenClose = item
                            firstData      = data
                        } else {
                            this.disable()
                            secondOpenClose = item
                            secondData      = data

                            if (firstData?.id != secondData?.id) {
                                this.clearActions()

                                // fail
                                gdxGame.soundUtil.apply { play(error) }

                                animDelay(0.4f) {
                                    firstOpenClose?.enable()
                                    secondOpenClose?.enable()

                                    firstOpenClose?.close()
                                    secondOpenClose?.close {
                                        firstOpenClose  = null
                                        secondOpenClose = null
                                        this.enable()
                                    }
                                }
                            } else {
                                this.clearActions()

                                // win
                                gdxGame.soundUtil.apply { play(win) }

                                animDelay(0.4f) {
                                    this.enable()

                                    firstOpenClose  = null
                                    secondOpenClose = null

                                    if (++countPair == 12) gdxGame.navigationManager.navigate(ResultWinScreen::class.java.name)
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun addItem3() {
        var newX = 0f
        var newY = 0f

        val listPos = listOf(
            Vector2(130f, 1117f),
            Vector2(130f+16f+125f, 1117f),
            Vector2(130f+16f*2f+125f*2f, 1117f),
            Vector2(130f+16f*3f+125f*3f, 1117f),
            Vector2(130f+16f*4f+125f*4f, 1117f),
            Vector2(130f+16f*5f+125f*5f, 1117f),
            Vector2(130f, 970f),
            Vector2(130f+16f+125f, 970f),
            Vector2(130f+16f*2f+125f*2f, 970f),
            Vector2(130f+16f*3f+125f*3f, 970f),
            Vector2(130f+16f*4f+125f*4f, 970f),
            Vector2(130f+16f*5f+125f*5f, 970f),

            Vector2(130f, 824f),
            Vector2(130f+16f+125f, 824f),
            Vector2(130f+16f*2f+125f*2f, 824f),
            Vector2(130f+16f*3f+125f*3f, 824f),
            Vector2(130f+16f*4f+125f*4f, 824f),
            Vector2(130f+16f*5f+125f*5f, 824f),
            Vector2(130f, 678f),
            Vector2(130f+16f+125f, 678f),
            Vector2(130f+16f*2f+125f*2f, 678f),
            Vector2(130f+16f*3f+125f*3f, 678f),
            Vector2(130f+16f*4f+125f*4f, 678f),
            Vector2(130f+16f*5f+125f*5f, 678f),
        )

        dataList.shuffled().onEachIndexed { index, data ->
            OpenerCloser(screen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                newX = listPos[index].x
                newY = listPos[index].y

                item.setBounds(newX, newY, 125f, 125f)

                item.setOnClickListener(gdxGame.soundUtil) {
                    item.disable()
                    item.open {
                        if (firstOpenClose == null) {
                            firstOpenClose = item
                            firstData      = data
                        } else {
                            this.disable()
                            secondOpenClose = item
                            secondData      = data

                            if (firstData?.id != secondData?.id) {
                                this.clearActions()

                                // fail
                                gdxGame.soundUtil.apply { play(error) }

                                animDelay(0.4f) {
                                    firstOpenClose?.enable()
                                    secondOpenClose?.enable()

                                    firstOpenClose?.close()
                                    secondOpenClose?.close {
                                        firstOpenClose  = null
                                        secondOpenClose = null
                                        this.enable()
                                    }
                                }
                            } else {
                                this.clearActions()

                                // win
                                gdxGame.soundUtil.apply { play(win) }

                                animDelay(0.4f) {
                                    this.enable()

                                    firstOpenClose  = null
                                    secondOpenClose = null

                                    if (++countPair == 12) gdxGame.navigationManager.navigate(ResultWinScreen::class.java.name)
                                }
                            }

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