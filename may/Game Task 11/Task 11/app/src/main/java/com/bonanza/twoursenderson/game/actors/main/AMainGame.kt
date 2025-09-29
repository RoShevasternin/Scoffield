package com.bonanza.twoursenderson.game.actors.main

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.bonanza.twoursenderson.game.actors.OpenClose
import com.bonanza.twoursenderson.game.actors.WTF
import com.bonanza.twoursenderson.game.screens.GameScreen
import com.bonanza.twoursenderson.game.utils.Block
import com.bonanza.twoursenderson.game.utils.TIME_ANIM_SCREEN
import com.bonanza.twoursenderson.game.utils.actor.*
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedMainGroup
import com.bonanza.twoursenderson.game.utils.gdxGame

class AMainGame(override val screen: GameScreen): AdvancedMainGroup() {

    data class Data(
        val id    : Int,
        val region: TextureRegion,
    )

    private val list10   by lazy { List(10) { index -> Data(index.inc(), gdxGame.assetsAll.listItems[index]) } }
    private val dataList by lazy { list10 + list10 }

    private var firstOpenClose : WTF? = null
    private var secondOpenClose: WTF? = null

    private var firstData : Data?    = null
    private var secondData: Data?    = null

    private var countPair = 0

    override fun addActorsOnGroup() {
        color.a = 0f

        addItems()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addItems() {
        var newX = 132f
        var newY = 907f

        dataList.shuffled().onEachIndexed { index, data ->
            WTF(screen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                item.setBounds(newX, newY, 267f, 267f)
                newX += 100 + 267

                if (index.inc() % 5 == 0) {
                    newX = 132f
                    newY -= 30 + 267
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

                                    if (++countPair == 10) gdxGame.navigationManager.navigate(screen::class.java.name)
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