package com.light.cryptocurrency

import android.app.Application
import android.os.StrictMode
import com.light.cryptocurrency.di.BaseComponent
import com.light.cryptocurrency.di.DaggerAppComponent
import com.light.cryptocurrency.util.DebugThree
import timber.log.Timber

class App : Application() {

    lateinit var component: BaseComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(DebugThree())
        }

        component = DaggerAppComponent.builder()
            .application(this).build()

        Timber.d("%s", component.coinsRepo())
        Timber.d("%s", component.coinsRepo())

    }
}