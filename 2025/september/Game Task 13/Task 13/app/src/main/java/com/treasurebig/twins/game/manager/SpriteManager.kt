package com.treasurebig.twins.game.manager

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

        _1(TextureData("textures/all/1.png")),
        _2(TextureData("textures/all/2.png")),
        _3(TextureData("textures/all/3.png")),
        _4(TextureData("textures/all/4.png")),
        _5(TextureData("textures/all/5.png")),
        _6(TextureData("textures/all/6.png")),
        _7(TextureData("textures/all/7.png")),
        _8(TextureData("textures/all/8.png")),
        _9(TextureData("textures/all/9.png")),
        _10(TextureData("textures/all/10.png")),

        RELUS        (TextureData("textures/all/relus.png")),
        SETTINS     (TextureData("textures/all/settins.png")),

        BACK1 (TextureData("textures/all/back1.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}