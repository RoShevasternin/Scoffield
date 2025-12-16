package com.filermax.detoxer.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

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
        SPLASH(  TextureAtlasData("atlas/splash.atlas")  ),
        COMMON(  TextureAtlasData("atlas/common.atlas")  ),
        CLEAN(   TextureAtlasData("atlas/clean.atlas")   ),
        BOOST(   TextureAtlasData("atlas/boost.atlas")   ),
        BATTERY( TextureAtlasData("atlas/battery.atlas") ),
        COOLING( TextureAtlasData("atlas/cooling.atlas") ),
    }

    enum class EnumTexture(override val data: TextureData): ITexture {
       // BACKGROUND(TextureData("textures/background.png")),
    }



    enum class SplashRegion(override val region: TextureRegion): IRegion {
        LOADER( EnumAtlas.SPLASH.data.atlas.findRegion("loader") ),
        LOGO(   EnumAtlas.SPLASH.data.atlas.findRegion("logo")   ),
    }

    enum class CommonRegion(override val region: TextureRegion): IRegion {
        ARROWS(        EnumAtlas.COMMON.data.atlas.findRegion("arrows")        ),
        BACK(          EnumAtlas.COMMON.data.atlas.findRegion("back")          ),
        BATTERY_CHECK( EnumAtlas.COMMON.data.atlas.findRegion("battery_check") ),
        BATTERY_DEF(   EnumAtlas.COMMON.data.atlas.findRegion("battery_def")   ),
        BOOST_CHECK(   EnumAtlas.COMMON.data.atlas.findRegion("boost_check")   ),
        BOOST_DEF(     EnumAtlas.COMMON.data.atlas.findRegion("boost_def")     ),
        BTN(           EnumAtlas.COMMON.data.atlas.findRegion("btn")           ),
        CLEAN_CHECK(   EnumAtlas.COMMON.data.atlas.findRegion("clean_check")   ),
        CLEAN_DEF(     EnumAtlas.COMMON.data.atlas.findRegion("clean_def")     ),
        COOLING_CHECK( EnumAtlas.COMMON.data.atlas.findRegion("cooling_check") ),
        COOLING_DEF(   EnumAtlas.COMMON.data.atlas.findRegion("cooling_def")   ),
        DONE(          EnumAtlas.COMMON.data.atlas.findRegion("done")          ),
        LOADER(        EnumAtlas.COMMON.data.atlas.findRegion("loader")        ),
        SEPARATOR(     EnumAtlas.COMMON.data.atlas.findRegion("separator")     ),
    }

    enum class CleanRegion(override val region: TextureRegion): IRegion {
        ORB_DARK(   EnumAtlas.CLEAN.data.atlas.findRegion("orb_dark")   ),
        ORB_DEF(    EnumAtlas.CLEAN.data.atlas.findRegion("orb_def")    ),
        ORB_FINISH( EnumAtlas.CLEAN.data.atlas.findRegion("orb_finish") ),
        ORB_LIGHT(  EnumAtlas.CLEAN.data.atlas.findRegion("orb_light")  ),
        ORB_START(  EnumAtlas.CLEAN.data.atlas.findRegion("orb_start")  ),
    }

    enum class BoostRegion(override val region: TextureRegion): IRegion {
        MASK(      EnumAtlas.BOOST.data.atlas.findRegion("mask")      ),
        PANEL(     EnumAtlas.BOOST.data.atlas.findRegion("panel")     ),
        PROGRESS(  EnumAtlas.BOOST.data.atlas.findRegion("progress")  ),
        ROCKET(    EnumAtlas.BOOST.data.atlas.findRegion("rocket")    ),
        STAR_BIG(  EnumAtlas.BOOST.data.atlas.findRegion("star_big")  ),
        STAR_MINI( EnumAtlas.BOOST.data.atlas.findRegion("star_mini") ),
    }

    enum class BatteryRegion(override val region: TextureRegion): IRegion {
        FRAME(           EnumAtlas.BATTERY.data.atlas.findRegion("frame")           ),
        LIGHTNING(       EnumAtlas.BATTERY.data.atlas.findRegion("lightning")       ),
        MASK(            EnumAtlas.BATTERY.data.atlas.findRegion("mask")            ),
        PROGRESS_BLUE(   EnumAtlas.BATTERY.data.atlas.findRegion("progress_blue")   ),
        PROGRESS_ORANGE( EnumAtlas.BATTERY.data.atlas.findRegion("progress_orange") ),
    }

    enum class CoolingRegion(override val region: TextureRegion): IRegion {
        SNOWFLAKE(EnumAtlas.COOLING.data.atlas.findRegion("snowflake")),
    }


//    enum class SplashListRegion(override val regionList: List<TextureRegion>): IRegionList {
//        MAN( List(21) { EnumAtlas.MAN.data.atlas.findRegion("man (${it.inc()})") }),
//        FIRE(List(10) { EnumAtlas.FIRE.data.atlas.findRegion("fire (${it.inc()})") }),
//    }



    interface IAtlas {
        val data: TextureAtlasData
    }

    interface ITexture {
        val data: TextureData
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

    data class TextureData(
        val path: String,
    ) {
        lateinit var texture: Texture
    }

}