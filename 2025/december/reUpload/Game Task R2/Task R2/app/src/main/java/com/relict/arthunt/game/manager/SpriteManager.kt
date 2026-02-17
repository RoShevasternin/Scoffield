package com.relict.arthunt.game.manager

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
        LOADING(  AtlasData("atlas/loading.atlas")),
        ALL    (  AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Loading
        BACKICH     (TextureData("textures/loading/backich.png")),
        MIKELANDJELO(TextureData("textures/loading/mikelandjelo.png")),
        
        // All
        MASKA_FOR_PROGRESS(TextureData("textures/all/maska_for_progress.png")),
        OLY_LEVEL         (TextureData("textures/all/oly_level.png")),
        OLY_MENU          (TextureData("textures/all/oly_menu.png")),
        OLY_RULES         (TextureData("textures/all/oly_rules.png")),
        OLY_SETTINGS      (TextureData("textures/all/oly_settings.png")),
        OLYVERYBAD        (TextureData("textures/all/OlyVeryBad.png")),
        OLYVERYNICE       (TextureData("textures/all/OlyVeryNice.png")),

        a(TextureData("textures/a.png")),
        b(TextureData("textures/b.png")),
        c(TextureData("textures/c.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}