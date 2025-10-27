package com.bigwin.targetdash

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bigwin.targetdash.BigWin46Helper.Companion.FRAGMENT_ID
import com.bigwin.targetdash.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var helper: BigWin46Helper
    lateinit var frgmnt: BigWin46Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize(){
        setupViewAndNavigation()
        setup()
    }

    private fun setup(){
        if (!helper.checkInternet()) {
            showError()
        } else {
            helper.monitorInternetConnectivity()
            validation()
            processFlow()
        }
    }

    private fun validation() {
        helper.validateStoredData(this)
    }

    private fun showError() {
        helper.showError()
    }

    private fun hideWW(){
        frgmnt.goneWebView()
    }

    private fun processFlow() {
        lifecycleScope.launch(Dispatchers.Main) {
            helper.fragFlow.collect { fragmentId: Int ->
                requestedOrientation = when (fragmentId) {
                    FRAGMENT_ID -> {
                        hideWW()
                        setNavigationBarColor(R.color.black)
                        configureStartDestination(fragmentId)
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    this@MainActivity.packageName.hashCode() -> {
                        frgmnt.showAndOpenUrl("", helper.firstOpen())
                        setNavigationBarColor(R.color.white)
                        orientationFullUser()
                    }

                    else -> orientationFullUser()
                }
            }
        }
    }

    private fun orientationFullUser() = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

    override fun onResume() {
        super.onResume()
        frgmnt.onResume()
    }

    override fun onPause() {
        frgmnt.onPause()
        super.onPause()
    }

    fun exit() {
        helper.exit(this)
    }

    private fun setupViewAndNavigation() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeProcess(binding)
    }

    private fun makeProcess(binding: ActivityMainBinding) {
        helper = BigWin46Helper(this, lifecycleScope)
        frgmnt = BigWin46Fragment(this, helper)

        helper.setUIStatuses(binding)
        frgmnt.onCreate(lifecycleScope, binding.stFrame)
    }

    private fun configureStartDestination(@IdRes destinationId: Int) {
        findNavController(R.id.nav_host_fragment).run {
            navInflater.inflate(R.navigation.nav_graph).apply {
                setStartDestination(destinationId)
            }.also { setGraph(it, null) }
        }
    }

    private fun setNavigationBarColor(@ColorRes colorId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            window.navigationBarColor = ContextCompat.getColor(this@MainActivity, colorId)
        }
    }
}