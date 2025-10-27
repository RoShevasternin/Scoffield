package com.plinko.ballwinx100.wb.addon


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

    fun createChooser(): Intent {
        val chooserIntent = Intent.createChooser(contentIntent, "")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        return chooserIntent
    }

    fun photoUri() = (cameraIntent as CameraIntent).photoUri

    fun requestCode() = context.packageName.hashCode()

}