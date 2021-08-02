package com.light.cryptocurrency

import android.app.Application
import android.os.StrictMode
import com.light.cryptocurrency.util.DebugThree
import timber.log.Timber

class App : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(DebugThree())
        }

        component = DaggerAppComponent.builder()
            .application(this)!!.build()

    }
}