package com.treasurebig.twins.game.actors.main

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.treasurebig.twins.game.actors.WTF
import com.treasurebig.twins.game.actors.button.AButton
import com.treasurebig.twins.game.screens.GameScreen
import com.treasurebig.twins.game.utils.Block
import com.treasurebig.twins.game.utils.TIME_ANIM_SCREEN
import com.treasurebig.twins.game.utils.actor.*
import com.treasurebig.twins.game.utils.advanced.AdvancedMainGroup
import com.treasurebig.twins.game.utils.gdxGame
import com.treasurebig.twins.util.log

class AMainGame(override val screen: GameScreen): AdvancedMainGroup() {

    data class Data(
        val id    : Int,
        val region: Texture,
    )

    private val list10   by lazy { List(10) { index -> Data(index.inc(), gdxGame.assetsAll.listItems[index]) } }
    private val dataList by lazy { list10 + list10 }

    private var firstOpenClose : WTF? = null
    private var secondOpenClose: WTF? = null

    private var firstData : Data?    = null
    private var secondData: Data?    = null

    private var countPair = 0

    private val btnX = AButton(screen, AButton.Type.X)

    override fun addActorsOnGroup() {
        color.a = 0f

        addItems()
        addBtnX()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(1837f, 1028f, 122f, 122f)

        btnX.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addItems() {
        var newX = 278f
        var newY = 945f

        dataList.shuffled().onEachIndexed { index, data ->
            WTF(screen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                log("d ${index.inc()} = ${index.inc() % 5}")

                item.setBounds(
                    newX,
                    if (index.inc() % 5 != 0) {
                        if ((index.inc() % 5) % 2 == 0) newY - 73 else newY
                    } else newY,
                    198f, 198f
                )
                newX += 99 + 198

                if (index.inc() % 5 == 0) {
                    newX = 278f
                    newY -= 74 + 198
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