package com.fortunetiger.mystictrail.game

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.FragmentActivity
import com.fortunetiger.mystictrail.Gist
import com.fortunetiger.mystictrail.R
import com.fortunetiger.mystictrail.appContext
import com.fortunetiger.mystictrail.game.screens.MainActivityController
import com.fortunetiger.mystictrail.game.screens.MainActivityController.Companion.binding
import com.fortunetiger.mystictrail.game.screens.MainActivityController.Companion.startFragmentId
import com.fortunetiger.mystictrail.game.screens.log
import com.fortunetiger.mystictrail.game.screens.menu.player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : FragmentActivity() {

    private val thisActivity = this

    val mainActController by lazy { MainActivityController(this) }

    private var ORIGINAL_LINK = ""

    val mainSharedPreferences: SharedPreferences = appContext.getSharedPreferences("GlobalShared", MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(mainActController) {
            initialize()
            showLoader()
        }

        mainActController.coroutineMain.launch {
            with(mainActController) {

                val mainLink = mainSharedPreferences.getString("MainLink", "noLink") ?: "noLink"

                if (mainLink == "noLink") {
                    coroutineMain.launch {
                        val jsonData = withContext(Dispatchers.IO) { Gist.getDataJson() }
                        jsonData?.let {
                            log("jsonData = $jsonData")

                            ORIGINAL_LINK = it.recognize

                            if (jsonData.prove == "true") {
                                initWeb()
                                loadUrl(ORIGINAL_LINK)
                            } else {
                                startFragmentId = R.id.menuFragment
                                goToNewScreen()
                            }
                        }
                    }
                } else {
                    WebFragment.GLOBAL_URL = mainLink
                    startFragmentId = R.id.webViewFragment
                    goToNewScreen()
                }
            }
        }
    }

    private fun goToNewScreen() {
        runOnUiThread {
            when (startFragmentId) {
                R.id.menuFragment -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                R.id.webViewFragment -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
            }
            mainActController.setStartDestination(startFragmentId)
            mainActController.hideLoader()
        }
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onResume() {
        super.onResume()
        player?.start()
    }

    // Init Web ----------------------------------------------------------------------------------

    private fun initWeb() {
        runOnUiThread {
            binding.webView.webViewClient = WebClient()
        }
    }

    fun loadUrl(url: String) {
        runOnUiThread {
            log("loadUrl: $url")
            binding.webView.loadUrl(url)
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            log("redirect: ${request?.url.toString()}")
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            log("onPageFinished: url = $url")

            if (url.toString() == ORIGINAL_LINK || url == null) {
                log("CLOSE go to Game")
                startFragmentId = R.id.menuFragment
                goToNewScreen()
            } else {
                log("CLOSE go to Web")
                WebFragment.GLOBAL_URL = url
                mainSharedPreferences.edit().putString("MainLink", WebFragment.GLOBAL_URL).apply()

                startFragmentId = R.id.webViewFragment
                goToNewScreen()
            }
        }
    }

}