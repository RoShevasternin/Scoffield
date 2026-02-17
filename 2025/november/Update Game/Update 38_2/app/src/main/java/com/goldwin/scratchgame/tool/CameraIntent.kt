package com.goldwin.scratchgame.tool

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

class CameraIntent(camera: Camera) : Intent(MediaStore.ACTION_IMAGE_CAPTURE) {

    constructor(context: Context) : this(Camera(context))

    val photoUri: Uri by lazy { camera.photoUri() }

    init {
        putExtra(
            KEY_MEDIA_STORE_EXTRA_OUTPUT,
            photoUri
        )
    }

    companion object {
        private const val KEY_MEDIA_STORE_EXTRA_OUTPUT = MediaStore.EXTRA_OUTPUT

    }

}