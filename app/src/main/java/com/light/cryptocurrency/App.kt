package com.light.cryptocurrency

import android.app.Application
import android.os.StrictMode
import com.google.firebase.messaging.FirebaseMessaging
import com.cryptocurrency.core.di.BaseComponent
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
            .application(this)
            .build()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { instanceIdResult ->
            if (!instanceIdResult.isSuccessful) {
                Timber.d("fcm: %s", instanceIdResult.exception)
                return@addOnCompleteListener
            }
            Timber.d("fcm: %s", instanceIdResult.result)
        }

        Timber.d("%s", component.coinsRepo())
        Timber.d("%s", component.coinsRepo())

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}