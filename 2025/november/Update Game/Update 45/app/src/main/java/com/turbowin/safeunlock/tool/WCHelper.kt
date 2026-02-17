package com.turbowin.safeunlock.tool

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient.FileChooserParams
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class WCHelper(
    private val chooserProcess: ChooserProcess
): ActivityResultCallback<ActivityResult> {

    constructor(
        context: Context
    ) : this(
        ChooserProcess(context)
    )

    private var valueCallback: ValueCallback<Array<Uri>>? = null

    override fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data?.data == null) {
                valueCallback?.onReceiveValue(
                    arrayOf(
                        chooserProcess.photoUri()
                    )
                )
            } else {
                valueCallback?.onReceiveValue(
                    FileChooserParams.parseResult(result.resultCode, result.data)
                )
            }
            this.valueCallback = null
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            this.valueCallback?.onReceiveValue(null)
        }
    }

    fun onShowFileChooser(
        activity: Activity,
        filePathCallback: ValueCallback<Array<Uri>>?,
        resultLauncher: ActivityResultLauncher<Intent>
    ): Boolean {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA),
                chooserProcess.requestCode()
            )
            return false
        } else {
            this.valueCallback = filePathCallback
            resultLauncher.launch(
                chooserProcess.createChooser()
            )
            return true
        }
    }
}