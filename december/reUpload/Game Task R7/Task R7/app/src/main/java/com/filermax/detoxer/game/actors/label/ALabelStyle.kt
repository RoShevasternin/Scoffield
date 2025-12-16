package com.filermax.detoxer.game.actors.label

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.filermax.detoxer.game.manager.FontTTFManager

object ALabelStyle {

    fun style(type: Type, color: Color = Color.WHITE) = Label.LabelStyle(type.font, color)

    interface Type {
        val font: BitmapFont
    }

    object Mulish {
        enum class Bold(override val font: BitmapFont): Type {
            _45(FontTTFManager.BoldFont.font_45.font),
            _33(FontTTFManager.BoldFont.font_33.font),
            _30(FontTTFManager.BoldFont.font_30.font),
        }

        enum class ExtraBold(override val font: BitmapFont): Type {
            _119(FontTTFManager.ExtraBoldFont.font_119.font),
            _89( FontTTFManager.ExtraBoldFont.font_89.font),
            _67( FontTTFManager.ExtraBoldFont.font_67.font),
            _45( FontTTFManager.ExtraBoldFont.font_45.font),
        }

        enum class SemiBold(override val font: BitmapFont): Type {
            _33(FontTTFManager.SemiBoldFont.font_33.font),
        }

        enum class Medium(override val font: BitmapFont): Type {
            _33(FontTTFManager.MediumFont.font_33.font),
            _30(FontTTFManager.MediumFont.font_30.font),
            _26(FontTTFManager.MediumFont.font_26.font),
        }
    }

}