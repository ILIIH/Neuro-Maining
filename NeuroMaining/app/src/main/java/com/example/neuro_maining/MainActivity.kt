package com.example.neuro_maining

import android.Manifest
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.neuro_maining.broadcast_receivers.InternetConnectivityReceiver
import com.example.neuro_maining.broadcast_receivers.WiFiDirectBroadcastReceiver
import com.example.neuro_maining.services.NeuronMiningService
import com.example.neuro_maining.ui.custom_view.PlotView
import com.example.neuro_maining.ui.theme.NeuroMainingTheme

val points = listOf(listOf(
    3 to 200f,
    1 to 150f,
    14 to 100f,
    19 to 50f
) to Color.Green,
    listOf(
        3 to 220f,
        1 to 120f,
        19 to 0f
    ) to Color.Magenta)

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("neuro_maining")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(context = applicationContext)
        registerBroadcastsReceivers(context = applicationContext)
        checkAndRequestPermission()

        setContent {
            NeuroMainingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlotView(
                        points,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        promptEnableWifiGPS(this)
    }

    private fun checkAndRequestPermission() {
        val permissions = mutableListOf<String>()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.NEARBY_WIFI_DEVICES)
            }
        }

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 1)
        }
    }

    private fun promptEnableWifiGPS(context: Context) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!wifiManager.isWifiEnabled) {
            AlertDialog.Builder(context)
                .setTitle("Enable WiFi")
                .setMessage("This application requires WiFi to be enabled. Would you like to enable it?")
                .setPositiveButton("Yes") { _, _ ->
                    startActivity( Intent(Settings.ACTION_WIFI_SETTINGS));
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(context)
                .setTitle("Enable Location")
                .setMessage("This application requires location services to be enabled for Wi-Fi Direct. Would you like to enable it?")
                .setPositiveButton("Yes") { _, _ ->
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun startService(context: Context){
        if(isServiceRunning(serviceClass = NeuronMiningService::class.java, context = context )){
            val serviceIntent = Intent(this, NeuronMiningService::class.java)
            startService(serviceIntent)
        }
    }

    private fun registerBroadcastsReceivers(context: Context){
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(InternetConnectivityReceiver(), filter
        )

        val manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        val channel = manager.initialize(this, mainLooper, null)

        val receiver = WiFiDirectBroadcastReceiver(manager, channel)
        val intentFilter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }
        registerReceiver(receiver, intentFilter)
    }

    // native methods
    external fun Init_cpp_file(): String

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NeuroMainingTheme {
        Greeting("Android")
    }
}