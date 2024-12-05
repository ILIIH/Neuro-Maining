package com.example.neuro_maining.framework

import android.net.wifi.p2p.WifiP2pDevice

interface ConnectivityManagerI {
    fun connectToPeers(peers: List<WifiP2pDevice>) : Int
}