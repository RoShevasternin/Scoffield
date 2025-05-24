package com.plinkray.ioosudmarine.game.utils.advanced

import com.plinkray.ioosudmarine.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}