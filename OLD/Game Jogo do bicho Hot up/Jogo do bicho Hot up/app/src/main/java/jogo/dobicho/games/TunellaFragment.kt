package jogo.dobicho.games

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

class TunellaFragment(private val activity: MainActivity) {

    var backBlock: () -> Unit = { activity.exit() }

    private var webView  : WebView?        = null
    private var coroutine: CoroutineScope? = null

    private var isVisible = false

    fun onCreate(coroutine: CoroutineScope, webYou: WebView) {
        onBackPressed()

        this.coroutine = coroutine
        webView = webYou.apply {
            with(settings) {
                savePassword = true
                saveFormData = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                setSupportZoom(true)
                useWideViewPort = true
                allowFileAccess = true
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

            webChromeClient = TunellaChromeClient(activity)
            webViewClient   = WdcClient(activity)

            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

            setDownloadListener(getDownloadListener())
        }
    }

    fun showAndOpenUrl() {
        isClearHistory = true

        isVisible = true
        coroutine?.launch(Dispatchers.Main) {
            webView?.visibility = View.VISIBLE
            webView?.loadUrl(MainActivity.tuNellaUrL)
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

    private fun onBackPressed() {
        activity.onBackPressedDispatcher.addCallback(activity) {
            if (webView?.canGoBack() == true) webView?.goBack() else backBlock()
        }
    }

}