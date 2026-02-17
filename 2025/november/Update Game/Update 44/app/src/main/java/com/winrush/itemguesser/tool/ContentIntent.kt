package com.winrush.itemguesser.tool

import android.content.Intent

class ContentIntent : Intent(ACTION_GET_CONTENT) {
    init {
        type = TYPE_IMG
        addCategory(CATEGORY_OPENABLE)
    }

    companion object {
        private const val TYPE_IMG = "image/*"
    }
}