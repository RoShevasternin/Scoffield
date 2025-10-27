package com.wingame.scratchwin

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.wingame.scratchwin.WinGam38Helper.Companion.FRAGMENT_ID
import com.wingame.scratchwin.game.AppDataStore

var isClearHistory = true

class WVClient(
    private val activity: MainActivity,
    private val helper: WinGam38Helper
) : WebViewClient() {

    private lateinit var intent: Intent
    private val dataStore = AppDataStore(activity.applicationContext)


//    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
//        try {
//            when {
//                url.startsWith("mailto:")               -> {
//                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
//                    intent.putExtra(Intent.EXTRA_TEXT, "Email body")
//                    activity.startActivity(intent)
//                }
//
//                url.startsWith("whatsapp:")             -> {
//                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    intent.data = Uri.parse(url)
//                    activity.startActivity(intent)
//                }
//
//                url.startsWith("viber:")                -> {
//                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    intent.data = Uri.parse(url)
//                    activity.startActivity(intent)
//                }
//
//                url.startsWith("tel:")                  -> {
//                    intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse(url)
//                    activity.startActivity(intent)
//                }
//
//                url.startsWith("https://t.me/joinchat") -> {
//                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    activity.startActivity(intent)
//                }
//
//                url.startsWith("tg:")                   -> {
//                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    activity.startActivity(intent)
//                }
//
//                url.startsWith("https://diia")          -> {
//                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    intent.data = Uri.parse(url)
//                    activity.startActivity(intent)
//                }
//
//                Uri.parse(url).host == "localhost"      -> {
//                    MainActivity.startFragmentID.tryEmit(R.id.libGDXFragment)
//                }
//
//                else -> if (url.startsWith("http://") || url.startsWith("https://")) return false
//            }
//        } catch (e: Exception) {
//            log("shouldOverrideUrlLoading Exception: ${e.message}")
//        }
//        return true
//    }

    private fun whatsappPrefix() = "whatsapp:"
    private fun mailtoPrefix() = "mailto:"
    private fun viderPrefix() = "viber:"
    private fun telPrefix() = "tel:"

    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        try {
            when {
                url.startsWith(mailtoPrefix()) -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                    intent.putExtra(Intent.EXTRA_TEXT, "Email body")
                    activity.startActivity(intent)
                }

                url.startsWith(whatsappPrefix()) ||
                        url.startsWith(viderPrefix()) ||
                        url.startsWith(telPrefix()) ||
                        url.startsWith("https://t.me/joinchat") ||
                        url.startsWith("tg:") ||
                        url.startsWith("https://diia") -> {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(intent)
                }

                Uri.parse(url).host == "localhost" -> {
                    helper.fragFlow.tryEmit(FRAGMENT_ID)
                }

                else -> {
                    if (url.startsWith("http://") || url.startsWith("https://")) {
                        return false
                    }
                }
            }
        } catch (_: Exception) {
        }
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        helper.loaderStatus().hideLoader()
        if (isClearHistory) {
            isClearHistory = false
            view?.clearHistory()
        }

        if (activity.frgmnt.firstOpen && url != null) {
            activity.frgmnt.firstOpen = false
            val localUrl = helper.url()
            if (localUrl != "" && extractHostFromUrl(localUrl) == extractHostFromUrl(url)) {
                MainScope().launch(Dispatchers.Main) {
                    view?.loadUrl("about:blank")
                    activity.frgmnt.goneWebView()
                    helper.fragFlow.emit(FRAGMENT_ID)
                    helper.loaderStatus().hideLoader()

                    MainScope().launch(Dispatchers.IO) {
                        dataStore.updateKeyz { "winGam381" }
                        dataStore.updateLink { "" }
                    }

                }
            } else {
                MainScope().launch(Dispatchers.Main) {
                    helper.loaderStatus().hideLoader()
                    activity.frgmnt.showWebView()

                    // Update DataStoreManager for tracking web session
                    MainScope().launch(Dispatchers.IO) {
                        dataStore.updateKeyz { "winGam380" }
                        dataStore.updateLink { url }
                    }

                }
            }


        }

    }

    private fun extractHostFromUrl(url: String?): String? {
        return url?.toUri()?.host
    }

}