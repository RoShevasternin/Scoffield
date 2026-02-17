package com.piriamidone.twispinton.game.manager

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
        All(  AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Load
        background   (TextureData("textures/load/background.png")),
        loader_mask  (TextureData("textures/load/loader_mask.png")),
        loading      (TextureData("textures/load/loading.png")),
        phara        (TextureData("textures/load/phara.png")),
        progress     (TextureData("textures/load/progress.png")),
        progress_back(TextureData("textures/load/progress_back.png")),
        // All
        fail       (TextureData("textures/allll/fail.png")),
        gamer      (TextureData("textures/allll/gamer.png")),
        level      (TextureData("textures/allll/level.png")),
        menu       (TextureData("textures/allll/menu.png")),
        phara_panel(TextureData("textures/allll/phara_panel.png")),
        puzzle_mask(TextureData("textures/allll/puzzle_mask.png")),
        rules      (TextureData("textures/allll/rules.png")),
        sett       (TextureData("textures/allll/sett.png")),
        timer_mask (TextureData("textures/allll/timer_mask.png")),
        win        (TextureData("textures/allll/win.png")),
        _1         (TextureData("textures/allll/1.png")),
        _2         (TextureData("textures/allll/2.png")),
        _3         (TextureData("textures/allll/3.png")),
        _4         (TextureData("textures/allll/4.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}