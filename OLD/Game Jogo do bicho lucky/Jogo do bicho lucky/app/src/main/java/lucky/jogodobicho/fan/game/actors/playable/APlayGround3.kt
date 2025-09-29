package lucky.jogodobicho.fan.game.actors.playable

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.util.log

class APlayGround3(override val screen: AdvancedScreen): AAbstractPlayable() {

    private val graduality    = (0..8).shuffled().take(3)
    private val animalsRegion = mutableListOf<TextureRegion>()
    private val foodsRegion   = mutableListOf<TextureRegion>()

    // Actor
    private val foodsImgList = mutableListOf<AMovableImage>()

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
        var ny = 1121f
        animalsRegion.onEach { region ->
            Image(region).also { img ->
                addActor(img)
                img.setBounds(200f, ny, 260f, 260f)
                ny -= (35f+260f)
            }
        }
    }

    private fun addFoods() {
        foodsRegion.onEachIndexed { index, region ->
            AMovableImage(screen, region).also { img ->
                foodsImgList.add(img)
                img.id = graduality[index]
            }
        }

        var ny = 1121f
        foodsImgList.shuffled().onEachIndexed{ index, img ->
            img.images.addAll(foodsImgList)
            img.positionID = index.inc()
            addActor(img)
            img.setBounds(610f, ny, 260f, 260f)
            ny -= (35f+260f)
        }
    }

    override fun checkIsWin(win: ()->Unit, lose: ()->Unit) {
        var counter = 0
        graduality.onEachIndexed { index, _->
            if (index.inc() == foodsImgList[index].positionID) counter++
        }
        if (counter==3) win() else lose()
    }

}