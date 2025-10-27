package fortunetiger.com.tighrino.game.screens

import fortunetiger.com.tighrino.game.LibGDXGame
import fortunetiger.com.tighrino.game.utils.advanced.AdvancedScreen

class IncasExitScreen(override val game: LibGDXGame): AdvancedScreen() {

    override fun show() {
        game.navigationManager.exit()
        super.show()
    }

}