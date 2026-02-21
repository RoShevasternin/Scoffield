package com.junglesort.questern.util

import android.content.Intent
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri
import com.junglesort.questern.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WebViewHelper(val activity: MainActivity) {

    private var isFirstLoadUrl = false

    var blockPageFinished: (String) -> Unit = {}

    fun initWebAndLoadUrl(url: String) {
        activity.runOnUiThread {
            activity.binding.webView.init(WVC())
            loadUrl(url)
        }
    }

    private fun loadUrl(url: String) {
        log("loadUrl: $url")
        isFirstLoadUrl = true
        activity.binding.webView.loadUrl(url)
    }

    private fun WebView.init(wvc: WVC) {
        activity.runOnUiThread {
            settings.apply {
                allowContentAccess = true
                mediaPlaybackRequiresUserGesture = false
                setSupportMultipleWindows(true)
                cacheMode = WebSettings.LOAD_DEFAULT
                loadsImagesAutomatically = true
                mixedContentMode = 0
                domStorageEnabled = true
                userAgentString = userAgentString.replace("; wv", "")
                allowFileAccess = true
                javaScriptEnabled = listOf(true).first()
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
            }

            isFocusable = true
            isSaveEnabled = true
            isFocusableInTouchMode = true
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            CookieManager.getInstance().setAcceptCookie(true)

            webViewClient = wvc

            setDownloadListener { url, userAgent, contentDescription, mimetype, _ ->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = url.toUri()
                context.startActivity(i)
            }

        }
    }

    private var redirectJob: Job? = null

    inner class WVC : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            log("redirect to: $url")

//            isRedirectToGame = false
//
//            // 1. Перевірка на твій "технічний" редірект для виходу в гру
//            if (url.contains("https://default")) {
//                log("Action: CLOSE webview, go to Game")
//                isRedirectToGame = true
//                blockRedirect()
//                return true // Блокуємо, бо ми самі обробили логіку
//            }

            // Кожного разу, як бачимо редірект, скидаємо таймер
            redirectJob?.cancel()
            redirectJob = activity.coroutine.launch {
                delay(2000) // чекаємо 2 секунди спокою
                activity.runOnUiThread {
                    log("Timer finished. Final URL is: $url")
                    blockPageFinished(url)
                }
            }

            return false
        }

//        override fun onPageFinished(view: WebView, url: String?) {
//            log("onPageFinished: url = $url")
//            if (url == null) return
//
//            blockPageFinished(url)
//
//        }
    }

}