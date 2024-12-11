package com.example.neuro_maining.broadcast_receivers

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.NetworkInfo
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                Log.d("WiFiDirect", "Wi-Fi Direct state: $state")

                manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() {
                        Log.d("WiFiDirect", "Peer discovery started successfully")
                    }

                    override fun onFailure(reason: Int) {
                        Log.e("WiFiDirect", "Peer discovery failed with reason: $reason")
                    }
                })
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d("WiFiDirectReceiver", "no permission ACCESS_FINE_LOCATION ${ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) }")
                    return
                }
                if (ActivityCompat.checkSelfPermission( context, Manifest.permission.NEARBY_WIFI_DEVICES ) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Log.d("WiFiDirectReceiver", "no permission NEARBY_WIFI_DEVICES ${ActivityCompat.checkSelfPermission(context, Manifest.permission.NEARBY_WIFI_DEVICES) }")
                    return
                }
                Log.d("WiFiDirect", "Peer list changed")
                manager.requestPeers(channel) { peerList ->
                    val peers = peerList.deviceList
                    if (peers.isEmpty()) {
                        Log.d("WiFiDirect", "No peers found")
                    } else {
                        peers.forEach { device ->
                            val currentDeviceCof = WifiP2pConfig().apply {
                                deviceAddress = device.deviceAddress
                                wps.setup = WpsInfo.PBC
                            }
                            manager.connect(channel,currentDeviceCof, object : WifiP2pManager.ActionListener {
                                override fun onSuccess() {
                                    Log.d("WiFiDirect", "Connection initiated with ${device.deviceName}")
                                }

                                override fun onFailure(reason: Int) {
                                    Log.d("WiFiDirect", "Connection failed: $reason")
                                }
                            })
                            Log.d("WiFiDirect", "Peer: ${device.deviceName}")
                        }
                    }
                }
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)
                if (networkInfo?.isConnected == true) {
                    Log.d("WiFiDirect", "Devices connected")
                } else {
                    Log.d("WiFiDirect", "Devices disconnected")
                }
            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                Log.d("WiFiDirect", "This device changed")
            }
        }
    }
}

