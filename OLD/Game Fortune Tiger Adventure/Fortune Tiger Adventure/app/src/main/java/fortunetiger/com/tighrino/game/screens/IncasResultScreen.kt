package fortunetiger.com.tighrino.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import fortunetiger.com.tighrino.game.LibGDXGame
import fortunetiger.com.tighrino.game.screens.common.IncasLevelScreen
import fortunetiger.com.tighrino.game.screens.common.IncasMenuScreen
import fortunetiger.com.tighrino.game.utils.actor.setOnClickListener
import fortunetiger.com.tighrino.game.utils.advanced.AdvancedScreen
import fortunetiger.com.tighrino.game.utils.advanced.AdvancedStage
import fortunetiger.com.tighrino.game.utils.region

class IncasResultScreen(override val game: LibGDXGame): AdvancedScreen() {

    companion object {
        var isWin = true
    }

    override fun show() {
        setBackgrounds(if (isWin) game.allAssets.IncasPerfectly.region else game.allAssets.IncasVeryBad.region)
        super.show()
        if (isWin) game.soundUtil.apply { play(goodresult) } else game.soundUtil.apply { play(wrong_answer) }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        val play = Actor()
        val exit = Actor()
        addActors(play, exit)

        play.apply {
            setBounds(304f, 912f, 494f, 158f)
            setOnClickListener(game.soundUtil) { game.navigationManager.navigate(IncasLevelScreen::class.java.name) }
        }
        exit.apply {
            setBounds(304f, 695f, 494f, 158f)
            setOnClickListener(game.soundUtil) { game.navigationManager.navigate(IncasMenuScreen::class.java.name) }
        }
    }

}