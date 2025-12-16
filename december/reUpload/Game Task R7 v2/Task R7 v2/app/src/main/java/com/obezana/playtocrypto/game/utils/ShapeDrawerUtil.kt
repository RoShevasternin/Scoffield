//package com.obezana.playtocrypto.game.utils
//
//import com.badlogic.gdx.graphics.Color
//import com.badlogic.gdx.graphics.Pixmap
//import com.badlogic.gdx.graphics.Texture
//import com.badlogic.gdx.graphics.g2d.Batch
//import com.badlogic.gdx.graphics.g2d.TextureRegion
//import com.badlogic.gdx.utils.Disposable
//import space.earlygrey.shapedrawer.ShapeDrawer
//
//class ShapeDrawerUtil(val batch: Batch): Disposable {
//
//    private var texture: Texture? = null
//
//    val drawer = ShapeDrawer(batch, getRegion())
//
//    override fun dispose() {
//        texture?.dispose()
//    }
//
//    fun update() {
//        drawer.update()
//    }
//
//    private fun getRegion(color: Color = Color.WHITE): TextureRegion {
//        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
//        pixmap.setColor(color)
//        pixmap.drawPixel(0, 0)
//        val texture = Texture(pixmap)
//        pixmap.dispose()
//        return TextureRegion(texture, 0, 0, 1, 1)
//    }
//
//}