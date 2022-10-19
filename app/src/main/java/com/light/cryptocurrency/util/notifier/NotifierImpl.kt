package com.light.cryptocurrency.util.notifier

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.cryptocurrency.core.util.Notifier
import com.light.cryptocurrency.R
import com.cryptocurrency.core.util.RxScheduler
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotifierImpl @Inject constructor(
    private val context: Context,
    private val rxScheduler: RxScheduler
): Notifier {

    private val ntf = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun sendMessage(title: String, message: String, receiver: Class<*>): Completable =
        Completable.fromAction {
            val channelId = context.getString(R.string.default_channel_id)
            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, receiver)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    PendingIntent.FLAG_ONE_SHOT,
                    Bundle.EMPTY
                ))
                .build()
            ntf.notify(1, notification)
        }
            .startWith(createDefaultChannel())
            .subscribeOn(rxScheduler.main())

    private fun createDefaultChannel(): Completable =
        Completable.fromAction {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ntf.createNotificationChannel(
                    NotificationChannel(
                        context.getString(R.string.default_channel_id),
                        context.getString(R.string.default_channel_name),
                        NotificationManager.IMPORTANCE_LOW
                    )
                )
            }
        }
}