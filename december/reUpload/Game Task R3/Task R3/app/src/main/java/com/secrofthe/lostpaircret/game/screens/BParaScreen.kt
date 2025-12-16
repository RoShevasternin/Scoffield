package com.secrofthe.lostpaircret.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.secrofthe.lostpaircret.game.LibGDXGame
import com.secrofthe.lostpaircret.game.actors.ATile
import com.secrofthe.lostpaircret.game.actors.ATimer
import com.secrofthe.lostpaircret.game.actors.button.AButton
import com.secrofthe.lostpaircret.game.actors.checkbox.ACheckBox
import com.secrofthe.lostpaircret.game.utils.TIME_ANIM_T
import com.secrofthe.lostpaircret.game.utils.actor.animHide
import com.secrofthe.lostpaircret.game.utils.actor.setOnClickListener
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedScreen
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedStage
import com.secrofthe.lostpaircret.game.utils.region
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BParaScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val panelImg = Image(game.allAssets.itemkar)
    private val menuBtn  = AButton(this, AButton.Static.Type.MENU)
    private val pauseBox = ACheckBox(this, ACheckBox.Static.Type.PAUSE)
    private val timer    = ATimer(this)

    private val COUNT_PAIR = 6
    private val COLUMN = 3

    private var tile1: ATile? = null
    private var tile2: ATile? = null

    private var clickCounter = 0
    private var winCounter   = 0

    override fun show() {
        setBackBackground(game.loadingAssets.matrix.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()
        addPause()
        addTimer()
        addPanel()
        addPairs()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addMenu() {
        addActor(menuBtn)
        menuBtn.setBounds(57f, 1651f, 182f, 193f)
        menuBtn.setOnClickListener { stageUI.root.animHide(TIME_ANIM_T) { game.navigationManager.back() } }
    }

    private fun AdvancedStage.addPause() {
        addActor(pauseBox)
        pauseBox.setBounds(845f, 1649f, 182f, 192f)
        pauseBox.setOnCheckListener { isCheck -> timer.isPause = isCheck }
    }

    private fun AdvancedStage.addTimer() {
        addActor(timer)
        timer.setBounds(451f, 1701f, 177f, 93f)

        timer.goTime {
            ResultScreen.isWin = false
            game.navigationManager.navigate(ResultScreen::class.java.name)
        }
    }

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(94f, 415f, 877f, 1045f)
    }

    private fun AdvancedStage.addPairs() {
        val regions = game.allAssets.memories.shuffled().take(COUNT_PAIR)

        var n = 0
        val tiles = List<ATile>(COUNT_PAIR * 2) {
            if (it == COUNT_PAIR) n = COUNT_PAIR
            ATile(this@BParaScreen,it-n, regions[it-n])
        }

        var nx = 274f
        var ny = 1169f

        tiles.onEachIndexed { index, tile ->
            addActor(tile)
            tile.setBounds(nx, ny, 140f, 140f)

            nx += 48f + 140f
            if (index.inc() % COLUMN == 0) {
                nx = 274f
                ny -= 48f + 140f
            }

            tile.setOnClickListener(game.soundUtil) {
                clickCounter++

                if (clickCounter <= 2) {
                    tile.animOpen()

                    when (clickCounter) {
                        1 -> tile1 = tile
                        2 -> {
                            coroutine?.launch {
                                delay(650)
                                tile2 = tile

                                if (tile1?.id == tile2?.id) {
                                    winCounter++
                                    if (winCounter == COUNT_PAIR) {
                                        ResultScreen.isWin = true
                                        game.navigationManager.navigate(ResultScreen::class.java.name)
                                    }
                                } else {
                                    tile1?.animClose()
                                    tile2?.animClose()
                                }

                                clickCounter = 0
                            }
                        }
                    }
                }

            }
        }
    }

}