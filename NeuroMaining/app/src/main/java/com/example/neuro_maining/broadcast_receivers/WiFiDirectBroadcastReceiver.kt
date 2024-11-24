package com.example.neuro_maining.broadcast_receivers

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat


class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION == action) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d("WiFiDirectReceiver", "no permission ACCESS_FINE_LOCATION ${ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) }")
                }
                return
            }
            manager.requestPeers(
                channel
            ) { peerList ->
                val peers: List<WifiP2pDevice> =
                    ArrayList(peerList.deviceList)
                if (peers.isEmpty()) {
                    Toast.makeText(context, "No devices found", Toast.LENGTH_SHORT).show()
                } else {
                    for (device in peers) {
                        Toast.makeText(
                            context,
                            "Found device: " + device.deviceName,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

