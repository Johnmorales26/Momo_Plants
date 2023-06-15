package com.johndev.momoplants.common.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.utils.Constants.CHANNEL_ID

// el nombre de la acción a ejecutar por el botón en la notificación
const val ACTION_RECEIVED = "action_received"

@SuppressLint("MissingPermission")
fun simpleNotification(context: Context, notification: Notification){
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
