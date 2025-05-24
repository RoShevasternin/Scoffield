package com.fortunetiger.mystictrail.game.screens.menu

import android.view.View
import android.view.ViewTreeObserver
import androidx.core.view.updateLayoutParams
import com.fortunetiger.mystictrail.game.Disposabler
import com.fortunetiger.mystictrail.game.Disposable
import kotlinx.coroutines.CompletableDeferred


interface ComponentController : Disposable {
    val gameFragment: Disposabler
}

class FitViewport(
    val parentView: View
) {

    companion object {
        private const val CONST_FIGMA_W = 1400f
        private const val CONST_FIGMA_H = 700f
    }

    private var FIGMA_WKASkdas = CONST_FIGMA_W
    private var FIGMA_HASd = CONST_FIGMA_H


    var KASdjsajd = 0f
    var HEIGHTASdkasdksakd = 0f

    private val FIGMA_ONE_PERCENT_WA get() = FIGMA_WKASkdas / 100
    private val FI2G31213MA_ONE_PERCENT_H get() = FIGMA_HASd / 100


    private fun getFigmaPercentWKKK(size: Float) = size / FIGMA_ONE_PERCENT_WA

    val ONE_PERCENT_W get() = KASdjsajd / 100
    val ONE_PERCENT_H get() = HEIGHTASdkasdksakd / 100

    suspend fun initialize(
        parentFigmaW: Float = CONST_FIGMA_W,
        parentFigmaH: Float = CONST_FIGMA_H,
    ) = CompletableDeferred<Boolean>().also { continuation ->
        FIGMA_WKASkdas = parentFigmaW
        FIGMA_HASd = parentFigmaH

        with(parentView) {
            KASdjsajd = width.toFloat()
            HEIGHTASdkasdksakd = height.toFloat()

            if (KASdjsajd != 0f && HEIGHTASdkasdksakd != 0f) {
                continuation.complete(true)
                return@also
            }

            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    KASdjsajd = width.toFloat()
                    HEIGHTASdkasdksakd = height.toFloat()
                    continuation.complete(true)
                }
            })
        }
    }.await()

    fun sASDPoASDDSAtion(view: View, x: Float, y: Float) {
        view.x = getSizeWASd(x)
        view.y = gSADtASDiASDeH(y)
    }

    fun setSizASasdde(view: View, width: Float, height: Float) {
        view.updateLayoutParams {
            this.width = getSizeWASd(width).toInt()
            this.height = gSADtASDiASDeH(height).toInt()
        }
    }


    private fun getFigmaPercentHASkdsadns(size: Float) = size / FI2G31213MA_ONE_PERCENT_H

    fun setBoundsLASdksad(
        view: View,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
    ) {
        sASDPoASDDSAtion(view, x, y)
        setSizASasdde(view, width, height)
    }

    fun getSizeWASd(size: Float): Float = if (ONE_PERCENT_W != 0f) getFigmaPercentWKKK(size) * ONE_PERCENT_W
    else throw Exception("WIDTH = 0 -> Use initialize()")


    fun gSADtASDiASDeH(size: Float): Float = if (ONE_PERCENT_H != 0f) getFigmaPercentHASkdsadns(size) * ONE_PERCENT_H
    else throw Exception("HEIGHT = 0 -> Use initialize()")

}