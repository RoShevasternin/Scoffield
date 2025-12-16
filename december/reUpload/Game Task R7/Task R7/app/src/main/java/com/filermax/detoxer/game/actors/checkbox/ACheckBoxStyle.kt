package com.filermax.detoxer.game.actors.checkbox

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.filermax.detoxer.game.manager.SpriteManager

data class ACheckBoxStyle(
    val default: TextureRegion,
    val checked: TextureRegion,
) {
    companion object {
        val clean get() = ACheckBoxStyle(
            default = SpriteManager.CommonRegion.CLEAN_DEF.region,
            checked = SpriteManager.CommonRegion.CLEAN_CHECK.region,
        )
        val boost get() = ACheckBoxStyle(
            default = SpriteManager.CommonRegion.BOOST_DEF.region,
            checked = SpriteManager.CommonRegion.BOOST_CHECK.region,
        )
        val battery get() = ACheckBoxStyle(
            default = SpriteManager.CommonRegion.BATTERY_DEF.region,
            checked = SpriteManager.CommonRegion.BATTERY_CHECK.region,
        )
        val cooling get() = ACheckBoxStyle(
            default = SpriteManager.CommonRegion.COOLING_DEF.region,
            checked = SpriteManager.CommonRegion.COOLING_CHECK.region,
        )
    }
}