package com.example.neuro_maining.broadcast_receivers

import com.example.neuro_maining.services.NeuronMiningService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NeuronMiningRestartReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, NeuronMiningService::class.java)
            context.startService(serviceIntent)
        }
    }
}
