package com.johndev.momoplants.common.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.johndev.momoplants.common.utils.Constants.ORDER_ID_INTENT
import com.johndev.momoplants.trackModule.view.TrackActivity

class NotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == ACTION_RECEIVED){
            val intentTrack = Intent(context, TrackActivity::class.java).apply {
                putExtra(ORDER_ID_INTENT, intent.getStringExtra(ORDER_ID_INTENT))
            }
            context?.startActivity(intentTrack)
        }
    }
}