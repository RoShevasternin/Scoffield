package com.fortunetiger.mystictrail.game

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.fortunetiger.mystictrail.databinding.FragmentWebviewBinding

class WebFragment : Fragment() {

    companion object {
        var GLOBAL_URL = ""
    }

    private var viewsWebs = mutableListOf<WebView>()

    private var fileChooserValueCallback: ValueCallback<Array<Uri>>? = null
    private var fileChooserResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        fileChooserValueCallback?.onReceiveValue(if (it.resultCode == RESULT_OK) arrayOf(Uri.parse(it.data?.dataString)) else null)
    }

    private val mainActivity by lazy { requireActivity() as MainActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWebviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Реєструємо зворотний виклик у диспетчері
        mainActivity.onBackPressedDispatcher.addCallback(mainActivity) {
            if (viewsWebs.last().canGoBack()) {
                viewsWebs.last().goBack()
            } else {
                if (viewsWebs.size > 1) {
                    binding.root.removeView(viewsWebs.last())
                    viewsWebs.last().destroy()
                    viewsWebs.removeAt(viewsWebs.lastIndex)
                }
            }
        }

        binding.webView.init()
        loadUrl(GLOBAL_URL)
    }

    lateinit var binding: FragmentWebviewBinding

    // Init Web ----------------------------------------------------------------------------------

    fun loadUrl(url: String) {
        mainActivity.runOnUiThread {
            binding.webView.loadUrl(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WebView.init() {
        mainActivity.runOnUiThread {
            webChromeClient = customWCC()
            webViewClient = customWVC()
            isSaveEnabled = true
            isFocusableInTouchMode = true
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            setDownloadListener { url, _, _, _, _ -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
            CookieManager.getInstance().setAcceptCookie(true)
            layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
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


    fun customWCC() = object : WebChromeClient() {
        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
            val wv = WebView(mainActivity)
            wv.init()
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

    private fun customWVC() = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {}

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            return if (url.contains("https://m.facebook.com/oauth/error")) true
            else if (url.startsWith("http")) false
            else {
                try {
                    view.context.startActivity(Intent.parseUri(url, Intent.URI_INTENT_SCHEME))
                } catch (_: java.lang.Exception) { }
                true
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

}