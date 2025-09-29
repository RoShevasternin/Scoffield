package lucky.jogodobicho.fan.game.actors.playable

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.util.log

class APlayGround4(override val screen: AdvancedScreen): AAbstractPlayable() {

    private val graduality    = (0..8).shuffled().take(4)
    private val animalsRegion = mutableListOf<TextureRegion>()
    private val foodsRegion   = mutableListOf<TextureRegion>()

    // Actor
    private val foodsImgList = mutableListOf<AMovableImage2>()

    override fun addActorsOnGroup() {
        log("i = $graduality")
        graduality.onEach {
            animalsRegion.add(screen.game.gameAssets.animals[it])
            foodsRegion.add(screen.game.gameAssets.foods[it])
        }
        addAnimals()
        addFoods()
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun addAnimals() {
        var ny = 1178f
        animalsRegion.onEach { region ->
            Image(region).also { img ->
                addActor(img)
                img.setBounds(266f, ny, 190f, 190f)
                ny -= (30f+190f)
            }
        }
    }

    private fun addFoods() {
        foodsRegion.onEachIndexed { index, region ->
            AMovableImage2(screen, region).also { img ->
                foodsImgList.add(img)
                img.id = graduality[index]
            }
        }

        var ny = 1178f
        foodsImgList.shuffled().onEachIndexed{ index, img ->
            img.images.addAll(foodsImgList)
            img.positionID = index.inc()
            addActor(img)
            img.setBounds(646f, ny, 190f, 190f)
            ny -= (30f+190f)
        }
    }

    override fun checkIsWin(win: ()->Unit, lose: ()->Unit) {
        var counter = 0
        graduality.onEachIndexed { index, _->
            if (index.inc() == foodsImgList[index].positionID) counter++
        }
        if (counter==4) win() else lose()
    }

}