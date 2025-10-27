package com.luckychance.reversememo.tool

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Camera(private val context: Context) {

    fun photoUri(): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = imageFileName(timeStamp)
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val photoFile: File? = try {
            File.createTempFile(imageFileName, ".jpg", storageDir)
        } catch (ex: IOException) {
            null
        }

        photoFile?.let {
            val currentPhotoUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", it)
            return currentPhotoUri!!
        }

        throw RuntimeException(errorMessage())
    }

    private fun imageFileName(timeStamp: String) = "JPEG_${timeStamp}_"

    private fun errorMessage() = "Failed to create photo file"
}