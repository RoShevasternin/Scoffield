package com.plinko.ballwinx100.wb.addon


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

class CameraIntent(camera: Camera) : Intent(MediaStore.ACTION_IMAGE_CAPTURE) {

    constructor(context: Context) : this(Camera(context))

    val photoUri: Uri by lazy { camera.photoUri() }

    init {
        putExtra(
            MediaStore.EXTRA_OUTPUT,
            photoUri
        )
    }

}