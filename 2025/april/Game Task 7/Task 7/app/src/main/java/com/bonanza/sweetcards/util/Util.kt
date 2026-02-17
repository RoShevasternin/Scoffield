package com.bonanza.sweetcards.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

val Number.length: Int get() = toString().length

val Float.toMS: Long get() = (this * 1000).toLong()



fun log(message: String) {
    Log.i("Garte", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope) {
    coroutine.forEach { it.cancel() }
}