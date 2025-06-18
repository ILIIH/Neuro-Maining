package com.example.neuro_maining.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.neuro_maining.services.NeuronMiningService

class NeuronMiningRestartReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, NeuronMiningService::class.java)
            context.startService(serviceIntent)
        }
    }
}
