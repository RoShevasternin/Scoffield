package jogo.dobicho.games

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import jogo.dobicho.games.util.log

var isClearHistory = true

class WdcClient(val activity: MainActivity): WebViewClient() {

    private lateinit var intent: Intent

    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        try {
            when {
                url.startsWith("mailto:")               -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                    intent.putExtra(Intent.EXTRA_TEXT, "Email body")
                    activity.startActivity(intent)
                }

                url.startsWith("whatsapp:")             -> {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.data = Uri.parse(url)
                    activity.startActivity(intent)
                }

                url.startsWith("viber:")                -> {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.data = Uri.parse(url)
                    activity.startActivity(intent)
                }

                url.startsWith("tel:")                  -> {
                    intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse(url)
                    activity.startActivity(intent)
                }

                url.startsWith("https://t.me/joinchat") -> {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(intent)
                }

                url.startsWith("tg:")                   -> {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(intent)
                }

                url.startsWith("https://diia")          -> {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.data = Uri.parse(url)
                    activity.startActivity(intent)
                }

                Uri.parse(url).host == "localhost"      -> {
                    MainActivity.startFragmentID.tryEmit(R.id.libGDXFragment)
                }

                else -> if (url.startsWith("http://") || url.startsWith("https://")) return false
            }
        } catch (e: Exception) {
            log("shouldOverrideUrlLoading Exception: ${e.message}")
        }
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        activity.lottie.hideLoader()
        if (isClearHistory) {
            isClearHistory = false
            view?.clearHistory()
        }
    }

}