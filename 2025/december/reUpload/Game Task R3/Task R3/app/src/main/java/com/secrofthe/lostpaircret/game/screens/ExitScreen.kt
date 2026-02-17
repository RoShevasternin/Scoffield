package com.secrofthe.lostpaircret.game.screens

import com.secrofthe.lostpaircret.game.LibGDXGame
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedScreen

class ExitScreen(override val game: LibGDXGame): AdvancedScreen() {

    override fun show() {
        game.navigationManager.exit()
        super.show()
    }

}