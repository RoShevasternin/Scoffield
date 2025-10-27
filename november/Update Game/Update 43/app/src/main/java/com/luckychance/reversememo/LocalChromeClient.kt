package com.luckychance.reversememo

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.luckychance.reversememo.tool.WCHelper

class LocalChromeClient(private val activity: AppCompatActivity) : WebChromeClient() {

    private val wcHelper = WCHelper(activity.applicationContext)

    private val resultLauncher =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            wcHelper.onActivityResult(result)
        }

    private fun isActivityDestroyed() = activity().isDestroyed
    private fun activity() = activity

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {

        if (isActivityDestroyed()) return false

        return wcHelper.onShowFileChooser(
            activity,
            filePathCallback,
            resultLauncher
        )
    }

}

