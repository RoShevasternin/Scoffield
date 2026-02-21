package com.diceracers.drimmer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.diceracers.drimmer.databinding.ActivityMainBinding
import com.diceracers.drimmer.util.OneTime
import com.diceracers.drimmer.util.WebViewHelper
import com.diceracers.drimmer.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess
import androidx.core.net.toUri

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        var statusBarHeight = 0
        var navBarHeight    = 0
    }

    private val onceExit            = OneTime()
    private val onceSystemBarHeight = OneTime()

    lateinit var binding      : ActivityMainBinding
    lateinit var webViewHelper: WebViewHelper

    val coroutine = CoroutineScope(Dispatchers.Main)

    val windowInsetsController by lazy { WindowCompat.getInsetsController(window, window.decorView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initialize()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            onceSystemBarHeight.use {
                statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                navBarHeight    = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

                // hide Status or Nav bar (після встановлення їх розмірів)
                windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
                windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

//            if (binding.webView.isVisible) {
//                val imeBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
//                val navBottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
//                val totalBottom = maxOf(imeBottom, navBottom)
//
//                binding.root.setPadding(0, statusBarHeight, 0, totalBottom)
//                log("ime = $imeBottom | navBar = $navBarHeight | total = $totalBottom")
//            }

            WindowInsetsCompat.CONSUMED
        }
    }

    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch {
                finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webViewHelper = WebViewHelper(this)
    }

    fun openPrivacyPolicy() {
        val intent = Intent(Intent.ACTION_VIEW, "https://doc-hosting.flycricket.io/dice-drift-racers-privacy-policy/64592a81-4e4e-4d4b-98e1-42ff6bc5f4f4/privacy".toUri())
        startActivity(intent)
    }

    fun openInBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        } catch (e: Exception) {
            log("Error: Не вдалося відкрити браузер. ${e.message}")
        }
    }

}