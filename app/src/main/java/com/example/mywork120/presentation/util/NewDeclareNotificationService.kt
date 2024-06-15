package com.example.myworkbase.presentation.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat

class NewDeclareNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(theId: Int) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("mywork://all/$theId")
        )
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(
                0,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        }

        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setContentTitle("New Declare Added")
            .setContentText("The week of the new declare is $theId")
            .setContentIntent(pendingIntent)
            .addAction(
                androidx.customview.R.drawable.notification_bg,
                "To The New Week Sum",
                pendingIntent
            )
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}