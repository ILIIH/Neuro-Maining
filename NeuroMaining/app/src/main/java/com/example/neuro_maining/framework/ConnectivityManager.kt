package com.example.neuroMaining.framework

import android.net.wifi.p2p.WifiP2pDevice

class ConnectivityManager : ConnectivityManagerI {

    override fun connectToPeers(peers: List<WifiP2pDevice>): Int {
        peers.forEach {
        }
        return 1
    }
}
