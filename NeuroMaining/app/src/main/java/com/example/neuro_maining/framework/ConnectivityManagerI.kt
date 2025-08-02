package com.example.neuroMaining.framework

import android.net.wifi.p2p.WifiP2pDevice

interface ConnectivityManagerI {
    fun connectToPeers(peers: List<WifiP2pDevice>): Int
}
