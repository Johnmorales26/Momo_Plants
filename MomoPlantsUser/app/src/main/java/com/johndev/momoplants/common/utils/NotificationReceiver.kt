package com.johndev.momoplants.common.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants.ORDER_ID_INTENT
import com.johndev.momoplants.ui.ordersModule.view.OrdersFragmentDirections

class NotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == ACTION_RECEIVED){
            val orderId = intent.getStringExtra(ORDER_ID_INTENT)

        }
    }
}