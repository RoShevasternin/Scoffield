package com.plinko.ballwinx100.wb

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.plinko.ballwinx100.wb.addon.WCProcess

class LocalCClient(private val activity: AppCompatActivity) : WebChromeClient() {

    private val wcProcess = WCProcess(activity.applicationContext)

    private val launcher =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            wcProcess.onActivityResult(result)
        }

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        return wcProcess.onShowFileChooser(
            activity,
            filePathCallback,
            launcher
        )
    }

}

