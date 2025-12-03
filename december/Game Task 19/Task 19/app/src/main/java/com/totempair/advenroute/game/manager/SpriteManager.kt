package com.totempair.advenroute.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList    = mutableListOf<AtlasData>()
    var loadableTexturesList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    // Texture
    fun loadTexture() {
        loadableTexturesList.onEach { assetManager.load(it.path, Texture::class.java) }
    }

    fun initTexture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        LOADER(AtlasData("atlas/loader.atlas")),
        ALL(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        //L_BACKGROUND(TextureData("textures/loader/background.png")),

        B1   (TextureData("textures/all/b1.png")),
        B2   (TextureData("textures/all/b2.png")),
        B3   (TextureData("textures/all/b3.png")),
        BACK1(TextureData("textures/all/back1.png")),
        BACK2(TextureData("textures/all/back2.png")),
        FAIL (TextureData("textures/all/fail.png")),
        MENU (TextureData("textures/all/menu.png")),
        TRY  (TextureData("textures/all/try.png")),
        WELL (TextureData("textures/all/well.png")),
        WIN  (TextureData("textures/all/win.png")),

        ggg  (TextureData("textures/all/ggg.png")),
        ppf  (TextureData("textures/all/ppf.png")),
        ppp  (TextureData("textures/all/ppp.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}