package com.example.neuro_maining.broadcast_receivers

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import androidx.core.app.ActivityCompat


class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
) :
    BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION == action) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.NEARBY_WIFI_DEVICES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
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
                    // Show the list of devices
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

