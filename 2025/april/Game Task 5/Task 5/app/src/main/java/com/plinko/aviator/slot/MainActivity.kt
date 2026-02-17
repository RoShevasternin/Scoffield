package com.plinko.aviator.slot

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.plinko.aviator.slot.databinding.ActivityMainBinding
import com.plinko.aviator.slot.game.GDX_GLOBAL_isPauseGame
import com.plinko.aviator.slot.game.GDX_ORIGINAL_LINK
import com.plinko.aviator.slot.game.utils.gdxGame
import com.plinko.aviator.slot.util.Lottie
import com.plinko.aviator.slot.util.Once
import com.plinko.aviator.slot.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        private var isRedirectToGame = false
    }

    private var viewsWebs = mutableListOf<WebView>()

    private var fileChooserValueCallback: ValueCallback<Array<Uri>>? = null
    private var fileChooserResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        fileChooserValueCallback?.onReceiveValue(if (it.resultCode == RESULT_OK) arrayOf(Uri.parse(it.data?.dataString)) else null)
    }

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = Once()

    lateinit var binding : ActivityMainBinding

    lateinit var lottie: Lottie

    var blockBack    : () -> Unit = {}
    var blockRedirect: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        lottie.showLoader()

        onBackPressedDispatcher.addCallback {
            if (viewsWebs.last().canGoBack()) {
                viewsWebs.last().goBack()
            } else {
                if (viewsWebs.size > 1) {
                    binding.root.removeView(viewsWebs.last())
                    viewsWebs.last().destroy()
                    viewsWebs.removeAt(viewsWebs.lastIndex)
                } else {
                    if (isOffer.not() && binding.webView.isVisible) {
                        hideWebView()
                    } else blockBack()
                }
            }
        }
    }

    override fun exit() {
        onceExit.once {

            log("exit")
            coroutine.launch(Dispatchers.Main) {
                finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lottie = Lottie(binding)
    }

    // Init Web ----------------------------------------------------------------------------------

    @Suppress("DEPRECATION")
    fun initWeb(webView: WebView) {
        coroutine.launch(Dispatchers.Main) {
            webView.apply {
                webChromeClient = customWCC()
                webViewClient = WebClient()
                isSaveEnabled = true
                isFocusableInTouchMode = true
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                setDownloadListener { url, _, _, _, _ -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
                CookieManager.getInstance().setAcceptCookie(true)
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                isFocusable = true
                setLayerType(View.LAYER_TYPE_HARDWARE, null)

                settings.apply {
                    loadWithOverviewMode = true
                    userAgentString = userAgentString.replace("; wv", "")
                    allowContentAccess = true
                    useWideViewPort = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                    loadsImagesAutomatically = true
                    mixedContentMode = 0
                    builtInZoomControls = true
                    mediaPlaybackRequiresUserGesture = false
                    setSupportMultipleWindows(true)
                    databaseEnabled = true
                    domStorageEnabled = true
                    javaScriptEnabled = true
                    displayZoomControls = false
                    allowFileAccess = true
                    javaScriptCanOpenWindowsAutomatically = true
                }

                viewsWebs.add(this)
            }
        }
    }

    private var isOffer = true

    fun loadUrl(url: String, isOffer: Boolean = true) {
        runOnUiThread {
            log("loadUrl: $url | isOffer = $isOffer")

            this.isOffer = isOffer
            binding.webView.loadUrl(url)
        }
    }

    fun customWCC() = object : WebChromeClient() {
        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
            val wv = WebView(this@MainActivity)
            initWeb(wv)
            binding.root.addView(wv)
            (resultMsg!!.obj as WebView.WebViewTransport).webView = wv
            resultMsg.sendToTarget()
            return true
        }

        override fun onShowFileChooser(w: WebView?, fpc: ValueCallback<Array<Uri>>?, fcp: FileChooserParams?): Boolean {
            try {
                fileChooserValueCallback = fpc
                fcp?.createIntent()?.let { fileChooserResultLauncher.launch(it) }
            } catch (_: ActivityNotFoundException) {
            }
            return true
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            log("redirect: ${request?.url.toString()}")

            isRedirectToGame = false

            if (request?.url.toString().contains("https://bebebe")) {
                log("contains: CLOSE go to Game")
                isRedirectToGame = true
                blockRedirect()
                return true
            }

            return false
        }

        var isFirstOpened = AtomicBoolean(true)

        override fun onPageFinished(view: WebView?, url: String?) {
            log("onPageFinished: url = $url")

            if (url.toString() == GDX_ORIGINAL_LINK || url == null) {
                log("CLOSE go to Game")
                blockRedirect()
            } else {
                if (url.toString().contains("about:blank").not() && isRedirectToGame.not()) {
                    log("onPageFinished showWebView url = $url")
                    if (binding.webView.isVisible.not()) {
                        showWebView()

                        if (isFirstOpened.getAndSet(false)) {
                            gdxGame.sharedPreferences.edit().putString("Pinokletka", url).apply()
                        }
                    }
                }
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewsWebs.lastOrNull()?.saveState(outState)
    }

    override fun onResume() {
        super.onResume()
        viewsWebs.lastOrNull()?.onResume().also {
            CookieManager.getInstance().flush()
        }
    }

    override fun onPause() {
        super.onPause()
        viewsWebs.lastOrNull()?.onPause().also {
            CookieManager.getInstance().flush()
        }
    }

    // Logic -----------------------------------------------------------------------------------------

    fun hideWebView() {
        runOnUiThread {
            binding.webView.visibility = View.GONE
            binding.webView.loadUrl("about:blank")

            binding.navHostFragment.requestFocus()
            GDX_GLOBAL_isPauseGame = false
            gdxGame.resume()
        }
    }

    fun showWebView() {
        runOnUiThread {
            binding.webView.visibility = View.VISIBLE

            binding.webView.requestFocus()
            GDX_GLOBAL_isPauseGame = true
            gdxGame.pause()
        }
    }

}