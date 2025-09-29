package lucky.jogodobicho.fan.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import lucky.jogodobicho.fan.game.screens.A44Screen
import lucky.jogodobicho.fan.game.utils.actor.setOnClickListener
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen

class AResultGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val result = Image()
    private val xEnd   = Image(screen.game.gameAssets.DAGGER)
    private val nEnd   = Image(screen.game.gameAssets.DIRECTION_TO_THE_RIGHT)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.gameAssets.RESULT))
        addResult()
        addBtns()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addResult() {
        addActor(result)
        result.setBounds(119f, 553f, 844f, 814f)
    }

    private fun addBtns() {
        addActors(xEnd, nEnd)
        xEnd.apply {
            setBounds(206f, 484f, 224f, 224f)
            setOnClickListener { screen.animHideScreen { screen.game.navigationManager.back() } }
        }
        nEnd.apply {
            setBounds(650f, 484f, 224f, 224f)
            setOnClickListener { screen.animHideScreen { screen.game.navigationManager.navigate(A44Screen::class.java.name) } }
        }
    }

    fun setResult(isWin: Boolean) {
        result.drawable = TextureRegionDrawable(if (isWin) screen.game.gameAssets.WINRAR else screen.game.gameAssets.LOSERAR)

    }

}