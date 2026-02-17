package com.luckywin.chestluck

import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.CookieManager
import android.webkit.DownloadListener
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.addCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LuckyWin39Fragment(
    private val activity: MainActivity,
    private val helper: LuckyWin39Helper
    ) {

    var shouldCloseWebViewOnBack = false
    var firstOpen = true

    var backBlock: () -> Unit = { activity.exit() }

    private var webView  : WebView?        = null
    private var coroutine: CoroutineScope? = null

    private var isVisible = false

    private fun setCoroutine(coroutine: CoroutineScope) {
        this.coroutine = coroutine
    }

    private fun savePassword() = true
    private fun saveFormData() = true
    private fun useWideViewPort() = true
    private fun allowFileAccess() = true

    fun onCreate(coroutine: CoroutineScope, webYou: WebView) {
        onBackPressed()
        setCoroutine(coroutine)
        webView = webYou.apply {
            with(settings) {
                savePassword = savePassword()
                saveFormData = saveFormData()
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                setSupportZoom(true)
                useWideViewPort = useWideViewPort()
                allowFileAccess = allowFileAccess()
                databaseEnabled = true
                useWideViewPort = true
                domStorageEnabled = true
                javaScriptEnabled = true
                displayZoomControls = false
                builtInZoomControls = true
                allowContentAccess = true
                loadWithOverviewMode = true
                loadsImagesAutomatically = true
                allowFileAccessFromFileURLs = true
                cacheMode = WebSettings.LOAD_DEFAULT
                allowUniversalAccessFromFileURLs = true
                javaScriptCanOpenWindowsAutomatically = true

//                setSupportMultipleWindows(true)
                setEnableSmoothTransition(true)

                pluginState = WebSettings.PluginState.ON
                setRenderPriority(WebSettings.RenderPriority.HIGH)
                userAgentString = "$userAgentString MobileAppClient/Android/0.9"
            }

            isFocusable = true
            isSaveEnabled = true
            isFocusableInTouchMode = true

            webChromeClient = LocalChromeClient(activity)
            webViewClient   = WVClient(activity, helper)

            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

            setDownloadListener(getDownloadListener())
        }
    }

    fun showAndOpenUrl(url: String, fOpen: Boolean = false) {
        isClearHistory = true
        firstOpen = fOpen

        var openUrl = url
        if (openUrl.length == 0) openUrl = helper.url()

        isVisible = false
        coroutine?.launch(Dispatchers.Main) {
            if(firstOpen) webView?.visibility = View.GONE
            else webView?.visibility = View.VISIBLE

            webView?.loadUrl(openUrl)
        }
    }

    fun showWebView() {
        isVisible = true
        coroutine?.launch(Dispatchers.Main) {
            webView?.visibility = View.VISIBLE
        }
    }

    fun goneWebView() {
        isVisible = false
        coroutine?.launch(Dispatchers.Main) {
            webView?.visibility = View.GONE
        }
    }

    fun onResume() {
        webView?.onResume()
    }

    fun onPause() {
        webView?.onPause()
    }




    private fun getDownloadListener() = DownloadListener { url, _, _, _, _ ->
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        activity.startActivity(intent)
    }

    fun enableOnBackPressed() {
        this.onBackPressed()
    }

    private fun onBackPressed() {
        activity.onBackPressedDispatcher.addCallback(activity) {
//            if (webView?.canGoBack() == true) webView?.goBack() else backBlock()
            when {
                webView?.canGoBack() == true -> webView?.goBack() // Navigate inside WebView
                isVisible && shouldCloseWebViewOnBack -> goneWebView() // Hide WebView if flag is set
                else -> backBlock() // Default behavior (exit app)
            }
        }
    }

}