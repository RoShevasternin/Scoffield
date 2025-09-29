package lucky.jogodobicho.fan.game.manager

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
        SPLASH(AtlasData("atlas/splash.atlas")),
        GAME(  AtlasData("atlas/game.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        MAIN_BACKGROUND(TextureData("textures/main_background.png")),
        IRON_MAN(       TextureData("textures/iron_man.png")),

        CHOOSE_RIGHT(   TextureData("textures/choose_right.png")),
        FLATTER_BLACK(  TextureData("textures/flatter_black.png")),
        LOSERAR(        TextureData("textures/loserar.png")),
        MENU_PRSE(      TextureData("textures/menu_prse.png")),
        PANEL_RULES(    TextureData("textures/panel_rules.png")),
        PANEL_SETTINGS( TextureData("textures/panel_settings.png")),
        RESULT(         TextureData("textures/result.png")),
        WINRAR(         TextureData("textures/winrar.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}