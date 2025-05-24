package com.plinko.x10ballsss

import android.app.Application
import android.content.Context

lateinit var appContext: Context private set

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}