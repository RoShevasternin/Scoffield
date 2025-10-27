package com.plinko.ballwinx100

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.plinko.ballwinx100.databinding.ActivityMainBinding
import com.plinko.ballwinx100.tool.Plinko37MainProcess
import com.plinko.ballwinx100.wb.Plinko37Frame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Plinko37Activity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    lateinit var plinko37MainProcess: Plinko37MainProcess
    lateinit var plmFrame: Plinko37Frame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewAndNavigation()

        if (!plinko37MainProcess.internetStatus().check()) {
            plinko37MainProcess.internetStatus().showError()
        } else {
            plinko37MainProcess.internetStatus().monitorInternetConnectivity()
            plinko37MainProcess.validateStoredData(this)

            lifecycleScope.launch(Dispatchers.Main) {
                plinko37MainProcess.startFragmentID.collect { fragmentId: Int ->
                    requestedOrientation = getOrientation(fragmentId)

                    if (fragmentId == R.id.libGDXFragment) {
                        plmFrame.goneWebView()
                        changeNavBarColor(R.color.black)
                        configureStartDestination(fragmentId)
                    } else if (fragmentId == this@Plinko37Activity.packageName.hashCode()) {
                        plmFrame.showAndOpenUrl("", plinko37MainProcess.firstOpen())
                        changeNavBarColor(R.color.white)
                    }
                }
            }
        }
    }

    private fun getOrientation(num: Int) = when (num) {
        R.id.libGDXFragment -> {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        this@Plinko37Activity.packageName.hashCode() -> {
            ActivityInfo.SCREEN_ORIENTATION_FULL_USER
        }
        else -> ActivityInfo.SCREEN_ORIENTATION_FULL_USER
    }

    override fun onResume() {
        super.onResume()
        plmFrame.onResume()
    }

    override fun onPause() {
        plmFrame.onPause()
        super.onPause()
    }

    override fun exit() {
        plinko37MainProcess.exit(this)
    }

    private fun setupViewAndNavigation() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        plinko37MainProcess = Plinko37MainProcess(this, lifecycleScope)
        plmFrame = Plinko37Frame(this, plinko37MainProcess)

        plinko37MainProcess.setUIStatuses(binding)
        plmFrame.onCreate(lifecycleScope, binding.frameW)
    }

    private fun configureStartDestination(@IdRes destinationId: Int) {
        findNavController(R.id.nav_host_fragment).run {
            navInflater.inflate(R.navigation.nav_graph).apply {
                setStartDestination(destinationId)
            }.also { setGraph(it, null) }
        }
    }

    fun changeNavBarColor(@ColorRes colorId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            window.navigationBarColor = ContextCompat.getColor(this@Plinko37Activity, colorId)
        }
    }
}