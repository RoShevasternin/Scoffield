package com.beeand.honeybonanza.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

fun log(message: String) {
    Log.i("Bekhhsa", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}