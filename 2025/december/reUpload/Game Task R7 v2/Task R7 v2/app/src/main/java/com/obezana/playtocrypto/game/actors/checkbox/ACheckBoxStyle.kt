package com.obezana.playtocrypto.game.actors.checkbox

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.obezana.playtocrypto.game.actors.button.AButtonStyle
import com.obezana.playtocrypto.game.manager.SpriteManager
import com.obezana.playtocrypto.game.utils.TextureEmpty
import com.obezana.playtocrypto.game.utils.region

data class ACheckBoxStyle(
    val default: TextureRegion,
    val checked: TextureRegion,
) {
    companion object {
        val galochka get() = ACheckBoxStyle(
            default = TextureEmpty.region,
            checked = SpriteManager.GameRegion.gallla.region,
        )
        val zamok get() = ACheckBoxStyle(
            default = SpriteManager.GameRegion.da.region,
            checked = SpriteManager.GameRegion.mada.region,
        )
        val pause get() = ACheckBoxStyle(
            default = SpriteManager.GameRegion.pausa.region,
            checked = SpriteManager.GameRegion.pla.region,
        )
    }
}