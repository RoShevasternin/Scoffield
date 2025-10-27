package com.turbowin.safeunlock

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import com.turbowin.safeunlock.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class Once {

    private var event = Event.NOT_WAS

    fun once(block: () -> Unit) {
        if (event.value == Event.NOT_WAS.value) {
            event = Event.WAS
            block()
        }
    }

    enum class Event(val value: Int) {
        WAS(0), NOT_WAS(1)
    }

}

class Lottie(private val binding: ActivityMainBinding) {

    private val coroutineLottie = CoroutineScope(Dispatchers.Main)

    fun showLoader() {
        coroutineLottie.launch {
            binding.loader.apply {
                if (isVisible.not()) {
                    isVisible = true
                    playAnimation()
                }
            }
        }
    }

    fun hideLoader() {
        coroutineLottie.launch {
            binding.loader.apply {
                if (isVisible) {
                    isVisible = false
                    coroutineLottie.coroutineContext.cancelChildren()
                }
            }
        }
    }

    fun showNotInternet() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.internet.apply {
                if (isVisible.not()) {
                    isVisible = true
                    playAnimation()
                }
            }
        }
    }

    fun hideNotInternet() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.internet.apply {
                if (isVisible) {
                    isVisible = false
                    cancelAnimation()
                }
            }
        }
    }
}

fun Context.internetConnection(): Boolean {
    var haveConnectedWifi   = false
    var haveConnectedMobile = false
    (getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager).allNetworkInfo.onEach { networkItem ->
        if (networkItem.typeName.equals(
                "WIFI",
                ignoreCase = true
            )
        ) if (networkItem.isConnected) haveConnectedWifi = true
        if (networkItem.typeName.equals(
                "MOBILE",
                ignoreCase = true
            )
        ) if (networkItem.isConnected) haveConnectedMobile = true
    }

    return haveConnectedWifi || haveConnectedMobile
}


abstract class AbstractDataStore() {

    abstract class DataStoreElement<T>(private val context: Context) {
        abstract val key: Preferences.Key<T>

        open suspend fun collect(block: suspend (T?) -> Unit) {
            context.applicationContext.dataStore.data.collect { block(it[key]) }
        }

        open suspend fun update(block: suspend (T?) -> T) {
            context.applicationContext.dataStore.edit { it[key] = block(it[key]) }
        }

        open suspend fun get(): T? {
            return context.applicationContext.dataStore.data.first()[key]
        }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "APP_DATA_STORE")

fun Context.isUSB() = Settings.Secure.getInt(contentResolver, keyAddEnabled(), 0) == 1

private fun keyAddEnabled() = Settings.Secure.ADB_ENABLED

fun Context.getBatteryPercentage(): Int {
    val bm = batteryManager()
    return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
}

fun Context.batteryManager() = getSystemService(BATTERY_SERVICE) as BatteryManager

fun Context.isBatteryCharging(): Boolean {
    val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
    return bm.isCharging
}