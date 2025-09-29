package lucky.jogodobicho.fan.game.actors.playable

import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup

abstract class AAbstractPlayable: AdvancedGroup() {
    abstract fun checkIsWin(win: ()->Unit, lose: ()->Unit)
}