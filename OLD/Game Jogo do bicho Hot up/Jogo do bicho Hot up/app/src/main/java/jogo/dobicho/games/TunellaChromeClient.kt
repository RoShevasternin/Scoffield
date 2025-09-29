package jogo.dobicho.games

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TunellaChromeClient(private val activity: MainActivity) : WebChromeClient() {

    companion object {
        const val CAMERA_PERMISSION_CODE = 60
    }

    private var currentPhotoUri: Uri?                = null
    private var filePath: ValueCallback<Array<Uri>>? = null

    private val resultLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data?.data == null) {
                filePath?.onReceiveValue(arrayOf(currentPhotoUri!!))
            } else {
                filePath?.onReceiveValue(
                    WebChromeClient.FileChooserParams.parseResult(result.resultCode, result.data)
                )
            }

            this.filePath = null
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            this.filePath?.onReceiveValue(null)
        }
    }

    override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            return false
        } else {
            this.filePath = filePathCallback

            val contentIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentIntent.type = "image/*"
            contentIntent.addCategory(Intent.CATEGORY_OPENABLE)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val cameraPhotoUri = createCameraPhotoUri()

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri)

            val chooserIntent = Intent.createChooser(contentIntent, "Choose an action")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

            this.resultLauncher.launch(chooserIntent)
            return true
        }
    }

    private fun createCameraPhotoUri(): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName     = "JPEG_${timeStamp}_"
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val photoFile: File? = try {
            File.createTempFile(imageFileName, ".jpg", storageDir)
        } catch (ex: IOException) {
            null
        }

        photoFile?.let {
            this.currentPhotoUri = FileProvider.getUriForFile(activity, "${activity.packageName}.fileprovider", it)
            return this.currentPhotoUri!!
        }

        throw RuntimeException("Failed to create photo file")
    }

}

