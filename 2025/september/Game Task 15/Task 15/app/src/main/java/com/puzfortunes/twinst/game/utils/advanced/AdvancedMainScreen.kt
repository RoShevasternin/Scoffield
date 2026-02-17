package com.puzfortunes.twinst.game.utils.advanced

abstract class AdvancedMainScreen : AdvancedScreen() {

    abstract val aMain: AdvancedMainGroup

    abstract fun AdvancedStage.addMain()

}