package com.light.cryptocurrency.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.light.cryptocurrency.App
import com.light.cryptocurrency.R
import com.light.cryptocurrency.ui.main.MainActivity
import com.cryptocurrency.core.util.Notifier
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class FcmService(): FirebaseMessagingService() {

    private val disposable = CompositeDisposable()

    @set:Inject
    var notifier: Notifier? = null

    override fun onCreate() {
        super.onCreate()
        val baseComponent = (application as App).component
        DaggerFcmComponent.builder().baseComponent(baseComponent).build().inject(this)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        val notification = p0.notification
        disposable.add(notifier?.sendMessage(
            Objects.toString(notification?.title, getString(R.string.app_name)),
            Objects.toString(notification?.body, "Something wrong!"),
        MainActivity::class.java)!!.subscribe())
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}