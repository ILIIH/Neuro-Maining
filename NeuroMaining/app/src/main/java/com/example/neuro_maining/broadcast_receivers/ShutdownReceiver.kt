package com.example.neuro_maining.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class ShutdownReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_SHUTDOWN == intent.action) {
            // Handle shutdown scenario, like making an API call
        }
    }
}

