package com.turbowin.gemshuffle.tool

import android.content.Context
import android.content.Intent

class ChooserProcess(
    private val context: Context,
    private val contentIntent: Intent,
    private val cameraIntent: Intent,
) {

    constructor(context: Context) : this(
        context,
        ContentIntent(),
        CameraIntent(context)
    )

    private fun title() = ""
    private fun keyExtraInitialIntents() = Intent.EXTRA_INITIAL_INTENTS

    fun createChooser(): Intent {
        val chooserIntent = Intent.createChooser(contentIntent, title())
        chooserIntent.putExtra(keyExtraInitialIntents(), arrayOf(cameraIntent))
        return chooserIntent
    }

    fun photoUri() = (cameraIntent as CameraIntent).photoUri

    fun requestCode() = context.packageName.hashCode()

}