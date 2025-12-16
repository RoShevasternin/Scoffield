package com.triplees.linees.game.manager

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
        ASSETS(AtlasData("atlas/assets.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Val Loader
        piramida(TextureData("textures/loader/piramida.png")),
        loa(TextureData("textures/loader/loa.png")),
        // Val All
        Fail      (TextureData("textures/all/Fail.png")),
        horizontal(TextureData("textures/all/horizontal.png")),
        menu      (TextureData("textures/all/menu.png")),
        rules     (TextureData("textures/all/rules.png")),
        settings  (TextureData("textures/all/settings.png")),
        vertical  (TextureData("textures/all/vertical.png")),
        Win       (TextureData("textures/all/Win.png")),
        you_lose  (TextureData("textures/all/you_lose.png")),
        you_win   (TextureData("textures/all/you_win.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}