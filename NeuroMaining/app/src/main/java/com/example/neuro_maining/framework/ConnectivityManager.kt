package com.example.neuro_maining.framework

import android.net.wifi.p2p.WifiP2pDevice

class ConnectivityManager:ConnectivityManagerI {

    override fun connectToPeers(peers: List<WifiP2pDevice>) : Int {

        return 1;
    }
}