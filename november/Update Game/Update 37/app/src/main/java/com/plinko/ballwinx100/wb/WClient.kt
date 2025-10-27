package com.plinko.ballwinx100.wb

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import com.plinko.ballwinx100.Plinko37Activity
import com.plinko.ballwinx100.R
import com.plinko.ballwinx100.util.log
import androidx.core.net.toUri
import com.plinko.ballwinx100.tool.Plinko37MainProcess
import com.plinko.ballwinx100.util.DataStoreManager

var isClearHistory = true

class WClient(
    private val activity: Plinko37Activity,
    private val plinko37MainProcess: Plinko37MainProcess
): WebViewClient() {

    private lateinit var intent: Intent

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

    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        try {
            when {
                url.startsWith("mailto:") -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                    intent.putExtra(Intent.EXTRA_TEXT, "Email body")
                    activity.startActivity(intent)
                }

                url.startsWith("whatsapp:") ||
                        url.startsWith("viber:") ||
                        url.startsWith("tel:") ||
                        url.startsWith("https://t.me/joinchat") ||
                        url.startsWith("tg:") ||
                        url.startsWith("https://diia") -> {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(intent)
                }

                Uri.parse(url).host == "localhost" -> {
                    plinko37MainProcess.startFragmentID.tryEmit(R.id.libGDXFragment)
                }

                else -> {
                    if (url.startsWith("http://") || url.startsWith("https://")) {
                        return false
                    }
                }
            }
        } catch (e: Exception) {
            log("shouldOverrideUrlLoading Exception: ${e.message}")
        }
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        plinko37MainProcess.loaderStatus().hide()
        if (isClearHistory) {
            isClearHistory = false
            view?.clearHistory()
        }

        log("url firstOpen: ${activity.plmFrame.firstOpen}")
        if(activity.plmFrame.firstOpen && url != null) {
            activity.plmFrame.firstOpen = false
            val localUrl = plinko37MainProcess.url()
            if (localUrl != "" && extractHostFromUrl(localUrl) == extractHostFromUrl(url)) {
                MainScope().launch(Dispatchers.Main) {
                    view?.loadUrl("about:blank")
                    activity.plmFrame.goneWebView()
                    plinko37MainProcess.startFragmentID.emit(R.id.libGDXFragment)
                    plinko37MainProcess.loaderStatus().hide()

                    MainScope().launch(Dispatchers.IO) {
                        DataStoreManager.Key.update { "plinko371" }
                        DataStoreManager.Link.update { "" }
                    }

                }
            } else {
                MainScope().launch(Dispatchers.Main) {
                    plinko37MainProcess.loaderStatus().hide()
                    activity.plmFrame.showWebView()

                    log("opening url: $url")

                    // Update DataStoreManager for tracking web session
                    MainScope().launch(Dispatchers.IO) {
                        DataStoreManager.Key.update { "plinko370" }
                        DataStoreManager.Link.update { url }
                    }

                }
            }


        }

    }

    fun extractHostFromUrl(url: String?): String? {
        return url?.toUri()?.host
    }

}