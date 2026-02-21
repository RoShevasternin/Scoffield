package com.junglesort.questern.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Disposable
import com.junglesort.questern.game.utils.advanced.AdvancedGroup
import com.junglesort.questern.util.log

class AScrollPane(private val group: AdvancedGroup): ScrollPane(group), Disposable {

    override fun dispose() {
        group.dispose()
    }

}