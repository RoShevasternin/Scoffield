package com.filermax.detoxer.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader

object FontTTFManager {

    private const val pathMulishBold      = "TTF/Mulish-Bold.ttf"
    private const val pathMulishExtraBold = "TTF/Mulish-ExtraBold.ttf"
    private const val pathMulishSemiBold  = "TTF/Mulish-SemiBold.ttf"
    private const val pathMulishMedium    = "TTF/Mulish-Medium.ttf"

    private val resolverInternal = InternalFileHandleResolver()

    var loadableListFont = mutableListOf<FontTTFData>()

//    val fontText: IFont get() = when(Language.locale.language) {
//        "ru", "uk" -> NotoSansFont
//        else -> MerriweatherSansFont
//    }



    private fun AssetManager.setLoaderTTF() {
        setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolverInternal))
        setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolverInternal))
    }

    private fun getLoaderParameter(
        path: String,
        parameters: FreeTypeFontGenerator.FreeTypeFontParameter.() -> Unit = { }
    ) = FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
        fontFileName = path
        fontParameters.apply {
            // characters  = FreeTypeFontGenerator.DEFAULT_CHARS + ("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя")
            minFilter   = TextureFilter.Linear
            magFilter   = TextureFilter.Linear
            incremental = true
            parameters()
        }
    }



    fun load(assetManager: AssetManager) {
        with(assetManager) {
            setLoaderTTF()
            loadableListFont.onEach { load(it.name  + ".ttf", BitmapFont::class.java, it.parameters) }
        }
    }

    fun init(assetManager: AssetManager) {
        loadableListFont.onEach { it.font = assetManager[it.name + ".ttf", BitmapFont::class.java] }
    }



    object BoldFont: IFont {
        val font_45 = FontTTFData("Bold_45", getLoaderParameter(pathMulishBold) { size = 45 })
        val font_33 = FontTTFData("Bold_33", getLoaderParameter(pathMulishBold) { size = 33 })
        val font_30 = FontTTFData("Bold_30", getLoaderParameter(pathMulishBold) { size = 30 })

        override val values: List<FontTTFData>
            get() = super.values + listOf(font_45, font_33, font_30)
    }

    object ExtraBoldFont: IFont {
        val font_119 = FontTTFData("ExtraBold_119", getLoaderParameter(pathMulishExtraBold) { size = 119 })
        val font_89  = FontTTFData("ExtraBold_89", getLoaderParameter(pathMulishExtraBold) { size = 89 })
        val font_67  = FontTTFData("ExtraBold_67", getLoaderParameter(pathMulishExtraBold) { size = 67 })
        val font_45  = FontTTFData("ExtraBold_45", getLoaderParameter(pathMulishExtraBold) { size = 45 })

        override val values: List<FontTTFData>
            get() = super.values + listOf(font_119, font_89, font_67, font_45)
    }

    object SemiBoldFont: IFont {
        val font_33 = FontTTFData("SemiBold_33", getLoaderParameter(pathMulishSemiBold) { size = 33 })

        override val values: List<FontTTFData>
            get() = super.values + listOf(font_33)
    }

    object MediumFont: IFont {
        val font_33 = FontTTFData("Medium_33", getLoaderParameter(pathMulishMedium) { size = 33 })
        val font_30 = FontTTFData("Medium_30", getLoaderParameter(pathMulishMedium) { size = 30 })
        val font_26 = FontTTFData("Medium_26", getLoaderParameter(pathMulishMedium) { size = 26 })

        override val values: List<FontTTFData>
            get() = super.values + listOf(font_33, font_30, font_26)
    }



    interface IFont {
        val values get() = listOf<FontTTFData>()
    }


    data class FontTTFData(
        val name: String,
        val parameters: FreetypeFontLoader.FreeTypeFontLoaderParameter,
    ) {
        lateinit var font: BitmapFont
    }
}