package com.plinki.kollinllabs.game.utils.advanced

import com.plinki.kollinllabs.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}