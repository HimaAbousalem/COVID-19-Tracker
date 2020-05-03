package com.iti.mobile.covid19tracker.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.model.entities.Country

fun makeStatusNotification(context: Context, changes: List<Country>): Notification {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH;
            val channel =
                     NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = "there is a changes in data"
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        }
        val builder =  NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Covid19 App")
                .setContentText("There is a change")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))

       // NotificationManagerCompat.from(context).notify(notificationId, builder.build());
        return builder.build();
    }