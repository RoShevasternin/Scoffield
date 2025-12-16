package com.obezana.playtocrypto.game.actors.image

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.obezana.playtocrypto.game.utils.advanced.AdvancedGroup

class AImage: AdvancedGroup {

    val image = Image()

    var drawable: Drawable = TextureRegionDrawable()
        get() = image.drawable
        set(value) {
            image.drawable = value
            field = value
        }


    constructor()
    constructor(region: TextureRegion) {
        image.drawable = TextureRegionDrawable(region)
    }
    constructor(texture: Texture) {
        image.drawable = TextureRegionDrawable(texture)
    }
    constructor(drawable: Drawable) {
        image.drawable = drawable
    }
    constructor(ninePatch: NinePatch) {
        image.drawable = NinePatchDrawable(ninePatch)
    }

    init { addActor(image) }

    override fun sizeChanged() {
        if (width > 0 && height > 0) image.setBounds(0f,0f,width, height)
    }

}