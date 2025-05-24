package com.bonanza.sweetcards.game.screens

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.bonanza.sweetcards.game.actors.Gold
import com.bonanza.sweetcards.game.actors.Grey
import com.bonanza.sweetcards.game.actors.OC
import com.bonanza.sweetcards.game.actors.button.AButton
import com.bonanza.sweetcards.game.manager.NavigationManager
import com.bonanza.sweetcards.game.manager.SpriteManager
import com.bonanza.sweetcards.game.util.Layout
import com.bonanza.sweetcards.game.util.advanced.AdvancedGroup
import com.bonanza.sweetcards.game.util.advanced.AdvancedScreen
import com.bonanza.sweetcards.game.util.advanced.AdvancedStage
import com.bonanza.sweetcards.game.util.disable
import com.bonanza.sweetcards.game.util.enable
import com.bonanza.sweetcards.game.util.listeners.toClickable
import com.bonanza.sweetcards.util.log
import com.bonanza.sweetcards.game.util.Layout.Game as LG

class GameScreen: AdvancedScreen() {

    private val dataList by lazy { List(20) { index ->
        Data(
            if (index >= 10) index - 9 else index.inc(),
            if (index >= 10) SpriteManager.ListRegion.ELEMENTS.regionList[index - 10] else SpriteManager.ListRegion.ELEMENTS.regionList[index]
        )
    } }

    var firstGroup : AdvancedGroup? = null
    var secondGroup: AdvancedGroup? = null
    var firstOC : OC? = null
    var secondOC: OC? = null
    var firstData : Data? = null
    var secondData: Data? = null

    var countPair = 0



    override fun show() {
        setBackBackground(SpriteManager.CommonRegion.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {

        val pos = Vector2(LG.grey.x, LG.grey.y)
        var newY = LG.gold.y
        var newX = LG.gold.x

        dataList.shuffled().onEachIndexed { index, data ->
            if (index <= 3) {
                Gold().also { gold ->
                    addActor(gold)
                    gold.image.drawable = TextureRegionDrawable(data.region)

                    with(LG.gold) {
                        gold.setBounds(newX, newY, w, h)
                        newX += w + hs
                    }

                    if (index == 1) {
                        newY = LG.y
                        newX = LG.gold.x
                    }

                    gold.toClickable().setOnClickListener {
                        gold.disable()
                        gold.open {
                            if (firstOC == null) {
                                firstGroup = gold
                                firstOC = gold
                                firstData = data
                            } else {
                                stageUI.root.disable()
                                secondGroup = gold
                                secondOC = gold
                                secondData = data

                                if (firstData?.id != secondData?.id) {
                                    this.root.apply {
                                        clearActions()
                                        addAction(Actions.sequence(
                                            Actions.delay(0.4f),
                                            Actions.run {
                                                firstOC?.close()
                                                secondOC?.close {
                                                    firstGroup?.enable()
                                                    secondGroup?.enable()
                                                    firstOC = null
                                                    secondOC = null
                                                    stageUI.root.enable()
                                                }
                                            }
                                        ))
                                    }
                                } else {
                                    this.root.apply {
                                        clearActions()
                                        addAction(Actions.sequence(
                                            Actions.delay(0.4f),
                                            Actions.run {
                                                stageUI.root.enable()
                                                firstGroup?.disable()
                                                secondGroup?.disable()

                                                firstOC = null
                                                secondOC = null

                                                if (++countPair == 10) NavigationManager.navigate(GameScreen())
                                            }))
                                    }
                                }
                            }
                        }

                    }

                }
            } else {
                Grey().also { grey ->
                    addActor(grey)
                    grey.image.drawable = TextureRegionDrawable(data.region)

                    when (index.inc()) {
                        10  -> pos.set(LG.g2)
                        16 -> pos.set(LG.g3)
                    }

                    with(LG.grey) {
                        grey.setBounds(pos.x, pos.y, w, h)
                        pos.x += w + hs
                    }

                    grey.toClickable().setOnClickListener {
                        grey.disable()
                        grey.open {
                            if (firstOC == null) {
                                firstGroup = grey
                                firstOC = grey
                                firstData = data
                            } else {
                                stageUI.root.disable()
                                secondGroup = grey
                                secondOC = grey
                                secondData = data

                                if (firstData?.id != secondData?.id) {
                                    this.root.apply {
                                        clearActions()
                                        addAction(
                                            Actions.sequence(
                                                Actions.delay(0.4f),
                                                Actions.run {
                                                    firstOC?.close()
                                                    secondOC?.close {
                                                        firstGroup?.enable()
                                                        secondGroup?.enable()
                                                        firstOC = null
                                                        secondOC = null
                                                        stageUI.root.enable()
                                                    }
                                                })
                                        )
                                    }
                                } else {

                                    this.root.apply {
                                        clearActions()
                                        addAction(
                                            Actions.sequence(
                                                Actions.delay(0.4f),
                                                Actions.run {
                                                    stageUI.root.enable()
                                                    firstGroup?.disable()
                                                    secondGroup?.disable()

                                                    firstOC = null
                                                    secondOC = null

                                                    if (++countPair == 10) NavigationManager.navigate(GameScreen())
                                                })
                                        )
                                    }
                                }
                            }
                        }

                    }

                }
            }
        }

    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    data class Data(
        val id: Int,
        val region: TextureRegion,
    )

}