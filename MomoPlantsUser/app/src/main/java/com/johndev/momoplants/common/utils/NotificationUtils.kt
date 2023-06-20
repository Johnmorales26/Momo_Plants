package com.johndev.momoplants.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.utils.Constants.CHANNEL_ID

// el nombre de la acción a ejecutar por el botón en la notificación
const val ACTION_RECEIVED = "action_received"

@SuppressLint("MissingPermission")
fun simpleNotification(context: Context, notification: Notification) {
    with(context) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_momo_plants_without_background)
            .setColor(getColor(R.color.md_theme_light_background))
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.order_track_update, notification.status))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        //lanzamos la notificación
        NotificationManagerCompat
            .from(this)
            .notify(20, builder.build()) // en este caso pusimos un id genérico
    }
}

@SuppressLint("MissingPermission")
fun buttonNotification(activity: Activity, notification: Notification) {
    val acceptIntent = Intent(activity, NotificationReceiver::class.java).apply {
        putExtra(Constants.ORDER_ID_INTENT, notification.id)
        action = ACTION_RECEIVED
    }
    val acceptPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(activity, 0, acceptIntent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(activity, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_momo_plants_without_background)
        .setContentTitle(activity.getString(R.string.app_name))
        .setContentText(activity.getString(R.string.order_track_update, notification.status))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .addAction(
            R.drawable.ic_momo_plants_without_background,
            activity.getString(R.string.notification_see_track),
            acceptPendingIntent
        )

    with(NotificationManagerCompat.from(activity)) {
        notify(20, builder.build())
    }
}