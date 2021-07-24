package com.light.cryptocurrency

import android.app.Application
import android.os.StrictMode

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) StrictMode.enableDefaults()
    }
}