package com.parrot.dicedash.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Disposable
import com.parrot.dicedash.game.utils.advanced.AdvancedGroup
import com.parrot.dicedash.util.log

class AScrollPane(private val group: AdvancedGroup): ScrollPane(group), Disposable {

    override fun dispose() {
        group.dispose()
    }

}