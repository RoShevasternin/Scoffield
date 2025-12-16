package com.piriamidone.twispinton.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.piriamidone.twispinton.game.LibGDXGame
import com.piriamidone.twispinton.game.utils.actor.setOnClickListener
import com.piriamidone.twispinton.game.utils.advanced.AdvancedScreen
import com.piriamidone.twispinton.game.utils.advanced.AdvancedStage
import com.piriamidone.twispinton.game.utils.region

class ResultScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var IS_WIN = true
    }

    override fun show() {
        if (IS_WIN) game.soundUtil.apply { play(POSITIVE) } else game.soundUtil.apply { play(NEGATIVE) }

        setBackgrounds(if (IS_WIN) game.alllAssets.win.region else game.alllAssets.fail.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtns()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBtns() {
        val cont = Actor()
        addActor(cont)
        cont.setBounds(305f, 1201f, 494f, 158f)
        cont.setOnClickListener(game.soundUtil) {
            animHideScreen { game.navigationManager.navigate(PuzzleScreen::class.java.name) }
        }

        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(305f, 987f, 494f, 158f)
            setOnClickListener(game.soundUtil) { animHideScreen {
                game.navigationManager.clearBackStack()
                game.navigationManager.navigate(MenuScreen::class.java.name)
            } }
        }
    }

}