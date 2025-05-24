package com.bonanza.sweetcards.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

object SpriteManager {

    var loadableAtlasList   = mutableListOf<IAtlas>()



    fun load(assetManager: AssetManager) {
        loadableAtlasList.onEach { assetManager.load(it.data.path, TextureAtlas::class.java) }
    }

    fun init(assetManager: AssetManager) {
        loadableAtlasList.onEach { it.data.atlas = assetManager[it.data.path, TextureAtlas::class.java] }
    }



    enum class EnumAtlas(override val data: TextureAtlasData): IAtlas {
        _1(TextureAtlasData("atlas/1.atlas")),
    }

    enum class CommonRegion(override val region: TextureRegion): IRegion {
        BACKGROUND(EnumAtlas._1.data.atlas.findRegion("background"))
    }

    enum class GameRegion(override val region: TextureRegion): IRegion {
        GOLD(EnumAtlas._1.data.atlas.findRegion("gold")),
        GRAY(EnumAtlas._1.data.atlas.findRegion("gray")),
    }

    enum class ListRegion(override val regionList: List<TextureRegion>): IRegionList {
        ELEMENTS(List(10) { EnumAtlas._1.data.atlas.findRegion("${it.inc()}") }),
    }



    interface IAtlas {
        val data: TextureAtlasData
    }

    interface IRegion {
        val region: TextureRegion
    }

    interface IRegionList {
        val regionList: List<TextureRegion>
    }

    data class TextureAtlasData(
        val path: String,
    ) {
        lateinit var atlas: TextureAtlas
    }

}