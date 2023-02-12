package com.geektech.taskmanager.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.geektech.taskmanager.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService:FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title
        val text = message.notification?.body
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = Notification.Builder(this, CHANNEL_ID)
        notification.setContentTitle(title)
        notification.setContentText(text)
        notification.setSmallIcon(R.drawable.ic_person)
        notification.setAutoCancel(true)
        NotificationManagerCompat.from(this).notify(1, notification.build())
    }

    companion object {
        const val CHANNEL_ID = "HEADS_UP_NOTIFICATION"
    }


}