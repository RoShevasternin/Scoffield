package com.diceracers.drimmer.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Disposable
import com.diceracers.drimmer.game.utils.advanced.AdvancedGroup
import com.diceracers.drimmer.util.log

class AScrollPane(private val group: AdvancedGroup): ScrollPane(group), Disposable {

    override fun dispose() {
        group.dispose()
    }

}