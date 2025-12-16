package com.secrofthe.lostpaircret.game.manager

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
        Loading(AtlasData("atlas/loading.atlas")),
        All    (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Loader
        fulikula(TextureData("textures/Loading/fulikula.png")),
        matrix  (TextureData("textures/Loading/matrix.png")),
        // All
        BF     (TextureData("textures/All/BF.png")),
        BW     (TextureData("textures/All/BW.png")),
        itemkar(TextureData("textures/All/itemkar.png")),
        menu   (TextureData("textures/All/menu.png")),
        rules  (TextureData("textures/All/rules.png")),
        sett   (TextureData("textures/All/sett.png")),
        porfix_mask  (TextureData("textures/All/porfix_mask.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}