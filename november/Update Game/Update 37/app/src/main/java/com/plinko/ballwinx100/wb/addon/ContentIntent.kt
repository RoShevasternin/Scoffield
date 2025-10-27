package com.plinko.ballwinx100.wb.addon

import android.content.Intent

class ContentIntent : Intent(ACTION_GET_CONTENT) {
    init {
        type = "image/*"
        addCategory(CATEGORY_OPENABLE)
    }
}