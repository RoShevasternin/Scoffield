package com.plinko.ballwinx100.tool


import android.content.Context
import com.plinko.ballwinx100.databinding.ActivityMainBinding
import com.plinko.ballwinx100.util.Lottie
import com.plinko.ballwinx100.util.internetConnection

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class InternetStatus(
    private val context: Context,
    private val scope: CoroutineScope,
    private val lottie: Lottie,
) {

    constructor(
        context: Context,
        scope: CoroutineScope,
        binding: ActivityMainBinding
    ) : this(
        context,
        scope,
        Lottie(binding)
    )

    fun check() = context.internetConnection()

    fun hideError() = lottie.hideNotInternet()

    fun showError() = lottie.showNotInternet()

    fun monitorInternetConnectivity() {
        scope.launch(Dispatchers.Main) {
            while (isActive) {
                delay(3000)
                if (this@InternetStatus.check()) {
                    this@InternetStatus.hideError()
                } else {
                    this@InternetStatus.showError()
                }

            }
        }
    }
}