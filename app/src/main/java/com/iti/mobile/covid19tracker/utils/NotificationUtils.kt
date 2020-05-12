package com.iti.mobile.covid19tracker.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.iti.mobile.covid19tracker.R

fun makeStatusNotification(context: Context, changes: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH;
            val channel =
                     NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = "Covid19 subscription info changes."
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        }
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.subscriptionsFragment)
            .createPendingIntent()

        val builder =  NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setContentTitle("Covid19 App")
                .setContentText(changes)
                .setStyle(NotificationCompat.BigTextStyle()
                .bigText(changes))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))
                .setAutoCancel(true)

       NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
       // return builder.build()
    }