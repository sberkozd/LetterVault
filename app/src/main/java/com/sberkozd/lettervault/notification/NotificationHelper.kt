package com.sberkozd.lettervault.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.sberkozd.lettervault.MainActivity
import com.sberkozd.lettervault.R

class NotificationHelper : BroadcastReceiver() {

    fun createNotificationChannel(
        context: Context,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)

            val channelId = "${context.packageName}-$name"

            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(channelId, name, importance)
                channel.description = description
                channel.setShowBadge(showBadge)

                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun sendNoteUnlockedNotification(
        context: Context,
        noteId: Int,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {

        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"

        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_add)
            setContentTitle(name)
            setContentText(description)
            setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    context.getString(R.string.note_unlocked)
                ))

                        priority = NotificationCompat . PRIORITY_HIGH
                        setAutoCancel(true)

                val bundle = Bundle ()
            bundle.putInt("id", noteId)

            val pendingIntent = NavDeepLinkBuilder(context)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.my_nav)
                .setDestination(R.id.detailFragment)
                .setArguments(bundle)
                .createPendingIntent()

            setContentIntent(pendingIntent)
        }
        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(1001, notificationBuilder.build())
    }

    override fun onReceive(context: Context?, intent: Intent?) {

    }
}