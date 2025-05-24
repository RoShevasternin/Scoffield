package com.plinko.aviator.slot.util

import android.util.Log
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

fun log(message: String) {
    Log.i("Tarasovka", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}