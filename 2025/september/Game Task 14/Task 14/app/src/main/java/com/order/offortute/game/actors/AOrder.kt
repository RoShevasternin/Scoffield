package com.order.offortute.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.order.offortute.game.utils.advanced.AdvancedGroup
import com.order.offortute.game.utils.advanced.AdvancedScreen
import com.order.offortute.game.utils.gdxGame
import com.order.offortute.util.log

class AOrder(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listRegions = MutableList(gdxGame.assetsAll.listItems.size) { gdxGame.assetsAll.listItems[it] }
    private val listImg = List(listRegions.size) { Image() }

    private val startIndexes = List(listRegions.size) { it }.shuffled()
    private val tmpIndexes   = MutableList(listRegions.size) { startIndexes[it] }

    override fun addActorsOnGroup() {
        var nx = 0f
        var ny = 578f

        listImg.forEachIndexed { index, img ->
            addActor(img)
            img.setBounds(nx, ny, 219f, 219f)

            img.drawable = TextureRegionDrawable(listRegions[startIndexes[index]])

            nx += 90 + 219f

            if (index.inc() % 2 == 0) {
                nx = 0f
                ny -= 70 + 219f
            }
        }

    }

    fun update() {
        tmpIndexes.shuffle()
        listImg.forEachIndexed { index,  img ->
            img.drawable = TextureRegionDrawable(listRegions[tmpIndexes[index]])
        }
    }

    fun checkItems(): Boolean {
        log("$startIndexes | $tmpIndexes | ${startIndexes == tmpIndexes}")
        return startIndexes == tmpIndexes
    }

}