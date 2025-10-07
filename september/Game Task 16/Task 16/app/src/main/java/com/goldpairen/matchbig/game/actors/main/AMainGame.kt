package com.goldpairen.matchbig.game.actors.main

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.goldpairen.matchbig.game.actors.WTF
import com.goldpairen.matchbig.game.actors.button.AButton
import com.goldpairen.matchbig.game.screens.GameScreen
import com.goldpairen.matchbig.game.screens.WinScreen
import com.goldpairen.matchbig.game.utils.Block
import com.goldpairen.matchbig.game.utils.TIME_ANIM_SCREEN
import com.goldpairen.matchbig.game.utils.actor.*
import com.goldpairen.matchbig.game.utils.advanced.AdvancedMainGroup
import com.goldpairen.matchbig.game.utils.gdxGame
import com.goldpairen.matchbig.util.log

class AMainGame(override val screen: GameScreen): AdvancedMainGroup() {

    data class Data(
        val id    : Int,
        val region: TextureRegion,
    )

    private val list15   by lazy { List(15) { index -> Data(index.inc(), gdxGame.assetsAll.items[index]) } }
    private val dataList by lazy { list15 + list15 }

    private var firstOpenClose : WTF? = null
    private var secondOpenClose: WTF? = null

    private var firstData : Data?    = null
    private var secondData: Data?    = null

    private var countPair = 0

    override fun addActorsOnGroup() {
        color.a = 0f

        val img = Image(gdxGame.assetsAll.fap)
        addActor(img)
        img.setBounds(249f, 1580f, 583f, 183f)

        addItems()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addItems() {
        var newX = 93f
        var newY = 1326f

        dataList.shuffled().onEachIndexed { index, data ->
            WTF(screen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                log("d ${index.inc()} = ${index.inc() % 5}")

                item.setBounds(newX, newY, 155f, 155f)
                newX += 33 + 155

                if (index.inc() % 5 == 0) {
                    newX = 93f
                    newY -= 35 + 155
                }

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
                                gdxGame.soundUtil.apply { play(fail) }

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

                                    if (++countPair == 15) gdxGame.navigationManager.navigate(WinScreen::class.java.name)
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