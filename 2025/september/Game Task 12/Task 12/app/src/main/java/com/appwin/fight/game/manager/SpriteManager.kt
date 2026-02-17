package com.appwin.fight.game.manager

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

        _1(TextureData("textures/all/1.jpg")),
        _2(TextureData("textures/all/2.jpg")),
        _3(TextureData("textures/all/3.jpg")),
        _4(TextureData("textures/all/4.jpg")),
        _5(TextureData("textures/all/5.jpg")),
        _6(TextureData("textures/all/6.jpg")),

        RULE (TextureData("textures/all/rule.png")),
        SONCE(TextureData("textures/all/sonce.png")),
        TEXT (TextureData("textures/all/text.png")),

        BACK1 (TextureData("textures/all/back1.png")),
        BACK2 (TextureData("textures/all/back2.png")),
        BACK3 (TextureData("textures/all/back3.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}