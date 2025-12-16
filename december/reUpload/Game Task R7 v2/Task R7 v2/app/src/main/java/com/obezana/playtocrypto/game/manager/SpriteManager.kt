package com.obezana.playtocrypto.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.obezana.playtocrypto.game.utils.region

object SpriteManager {

    var loadableAtlasList   = mutableListOf<IAtlas>()
    var loadableTextureList = mutableListOf<ITexture>()



    fun loadAtlas(assetManager: AssetManager) {
        loadableAtlasList.onEach { assetManager.load(it.data.path, TextureAtlas::class.java) }
    }

    fun loadTexture(assetManager: AssetManager) {
        loadableTextureList.onEach {
            assetManager.load(it.data.path, Texture::class.java, TextureLoader.TextureParameter().apply {
                minFilter = Texture.TextureFilter.Linear
                magFilter = Texture.TextureFilter.Linear
                genMipMaps = true
            })
        }
    }

    fun initAtlas(assetManager: AssetManager) {
        loadableAtlasList.onEach { it.data.atlas = assetManager[it.data.path, TextureAtlas::class.java] }
    }

    fun initTexture(assetManager: AssetManager) {
        loadableTextureList.onEach { it.data.texture = assetManager[it.data.path, Texture::class.java] }
    }



    enum class EnumAtlas(override val data: TextureAtlasData): IAtlas {
        GAME(   TextureAtlasData("atlas/game.atlas")   ),
    }

    enum class EnumTexture(override val data: TextureData): ITexture {
        LORAN(               TextureData("textures/spaha/loran.png")               ),
        XUESOS(              TextureData("textures/spaha/xuesos.png")              ),
        ZAGRUZGA_BACKGROUND( TextureData("textures/spaha/zagruzga_background.png") ),

        GREENCASTLE_BACKGROUND( TextureData("textures/GreenCastle_background.png") ),
        PICKACOINT_BACKGROUND(  TextureData("textures/PickACoint_background.png")  ),
        PURPLEHUETA_BACKGROUND( TextureData("textures/PurpleHueta_background.png") ),
        YROVNI_BACKGROUND(      TextureData("textures/Yrovni_background.png")      ),
    }


    enum class SplashRegion(val region: TextureRegion) {
        LORAN(               EnumTexture.LORAN.data.texture.region               ),
        XUESOS(              EnumTexture.XUESOS.data.texture.region              ),
        ZAGRUZGA_BACKGROUND( EnumTexture.ZAGRUZGA_BACKGROUND.data.texture.region ),
    }

    enum class GameRegion(val region: TextureRegion) {
        GREENCASTLE_BACKGROUND( EnumTexture.GREENCASTLE_BACKGROUND.data.texture.region ),
        PICKACOINT_BACKGROUND(  EnumTexture.PICKACOINT_BACKGROUND.data.texture.region  ),
        PURPLEHUETA_BACKGROUND( EnumTexture.PURPLEHUETA_BACKGROUND.data.texture.region ),
        YROVNI_BACKGROUND(      EnumTexture.YROVNI_BACKGROUND.data.texture.region      ),

        pla(                EnumAtlas.GAME.data.atlas.findRegion("pla")                ),
        da(                 EnumAtlas.GAME.data.atlas.findRegion("da")                 ),
        gallla(             EnumAtlas.GAME.data.atlas.findRegion("gallla")             ),
        gorochki_and_zamok( EnumAtlas.GAME.data.atlas.findRegion("gorochki_and_zamok") ),
        Gorshok(            EnumAtlas.GAME.data.atlas.findRegion("Gorshok")            ),
        mada(               EnumAtlas.GAME.data.atlas.findRegion("mada")               ),
        pausa(              EnumAtlas.GAME.data.atlas.findRegion("pausa")              ),
        pufik(              EnumAtlas.GAME.data.atlas.findRegion("pufik")              ),
        skala(              EnumAtlas.GAME.data.atlas.findRegion("skala")              ),
        taravavava(         EnumAtlas.GAME.data.atlas.findRegion("taravavava")         ),
        travka(             EnumAtlas.GAME.data.atlas.findRegion("travka")             ),
        Trrava(             EnumAtlas.GAME.data.atlas.findRegion("Trrava")             ),
        Uebok(              EnumAtlas.GAME.data.atlas.findRegion("Uebok")              ),
        ui_spin(            EnumAtlas.GAME.data.atlas.findRegion("ui_spin")            ),
        zelenka(            EnumAtlas.GAME.data.atlas.findRegion("zelenka")            ),
    }

//    enum class Game9Path(val ninePatch: NinePatch) {
//        JOINT(EnumAtlas.GAME.data.atlas.createPatch("joint")),
//    }

        enum class ListRegion(val regionList: List<TextureRegion>) {
            DOBRI( List(19) { EnumAtlas.GAME.data.atlas.findRegion("${it.inc()}") }),
        }



    interface IAtlas {
        val data: TextureAtlasData
    }

    interface ITexture {
        val data: TextureData
    }

    data class TextureAtlasData(
        val path: String,
    ) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(
        val path: String,
    ) {
        lateinit var texture: Texture
    }

}