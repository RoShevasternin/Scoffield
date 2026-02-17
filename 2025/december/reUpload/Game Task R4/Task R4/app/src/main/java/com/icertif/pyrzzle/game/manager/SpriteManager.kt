package com.icertif.pyrzzle.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTextureList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun loadTexture() {
        loadableTextureList.onEach {
            assetManager.load(it.path, Texture::class.java, TextureLoader.TextureParameter().apply {
                minFilter = Texture.TextureFilter.Linear
                magFilter = Texture.TextureFilter.Linear
                genMipMaps = true
            })
        }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    fun initTexture() {
        loadableTextureList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTextureList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        All   (AtlasData("atlas/all.atlas")),
        Loader(AtlasData("atlas/loader.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Load
        eqiptian(TextureData("textures/loader/eqiptian.png")),
        maska   (TextureData("textures/loader/maska.png")),
        // All
        gamek   (TextureData("textures/all/gamek.png")),
        ggg     (TextureData("textures/all/ggg.png")),
        level   (TextureData("textures/all/level.png")),
        menu    (TextureData("textures/all/menu.png")),
        miskagu (TextureData("textures/all/miskagu.png")),
        rrr     (TextureData("textures/all/rrr.png")),
        rules   (TextureData("textures/all/rules.png")),
        settings(TextureData("textures/all/settings.png")),
        bubinka (TextureData("textures/all/bubinka.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}