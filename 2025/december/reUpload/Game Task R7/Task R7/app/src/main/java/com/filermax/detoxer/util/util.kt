package com.filermax.detoxer.util

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.os.BatteryManager
import android.provider.Settings
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

fun log(message: String) {
    Log.i("Rat", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}

fun Context.isUSB() = Settings.Secure.getInt(contentResolver, Settings.Secure.ADB_ENABLED, 0) == 1

fun Context.getBatteryPercentage(): Int {
    val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
    return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
}

fun Context.isBatteryCharging(): Boolean {
    val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
    return bm.isCharging
}