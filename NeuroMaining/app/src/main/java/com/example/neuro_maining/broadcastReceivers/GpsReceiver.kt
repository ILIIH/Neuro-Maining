package com.example.neuroMaining.broadcastReceivers

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast

class GpsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (!isGpsEnabled) {
                AlertDialog.Builder(context)
                    .setTitle("Enable Location")
                    .setMessage("Neuron maining app requires location services to be enabled for Wi-Fi Direct. Would you like to enable it?")
                    .setPositiveButton("Yes") { _, _ ->
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                Toast.makeText(context, "GPS turned OFF", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
