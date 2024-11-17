package com.example.neuro_maining.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class LowBatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BATTERY_LOW == intent.action) {
            // Make the API call or handle the low battery scenario
        }
    }
}

