package com.fortunetiger.mystictrail.game.screens

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.fortunetiger.mystictrail.R
import com.fortunetiger.mystictrail.databinding.ActivityMainBinding
import com.fortunetiger.mystictrail.game.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityController(val activity: MainActivity) {

    companion object {
        lateinit var binding: ActivityMainBinding

        lateinit var navConKksakdkatroller: NavController

        @IdRes
        var startFragmentId = R.id.webViewFragment

    }

    val coroutineMain = CoroutineScope(Dispatchers.Default)

    fun initialize() {
        with(activity) {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            navConKksakdkatroller = findNavController(R.id.nav_host_fragment)
        }
    }

    fun showLoader() {
        coroutineMain.launch(Dispatchers.Main) {
            binding.lottie.apply {
                isVisible = true
                playAnimation()
            }
        }
    }

    fun hideLoader() {
        coroutineMain.launch(Dispatchers.Main) {
            binding.lottie.apply {
                isVisible = false
                cancelAnimation()
            }
        }
    }

    fun setStartDestination(
        @IdRes destinationId: Int,
        args: Bundle? = null
    ) {
        with(navConKksakdkatroller) {
            navInflater.inflate(R.navigation.nav_graph).apply { setStartDestination(destinationId) }
                .also { setGraph(it, args) }
        }
    }

}