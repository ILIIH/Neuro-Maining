package com.example.neuro_maining

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.neuro_maining.broadcastReceivers.InternetConnectivityReceiver
import com.example.neuro_maining.broadcastReceivers.WiFiDirectBroadcastReceiver
import com.example.neuro_maining.navigation.AppNavHost
import com.example.neuro_maining.navigation.BottomNavigationBar
import com.example.neuro_maining.services.NeuronMiningService
import com.example.neuro_maining.ui.theme.NeuroMainingTheme
import com.example.neuro_maining.ui.theme.PrimaryColor

class MainActivity : ComponentActivity() {
    companion object {
        init {
            System.loadLibrary("neuro_maining")
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = PrimaryColor.toArgb()

        startService(context = applicationContext)
        registerBroadcastsReceivers(context = applicationContext)
        checkAndRequestPermission()

        setContent {
            ChangeSystemBarsTheme(false)

            NeuroMainingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController)
                        }
                    ) {
                        AppNavHost(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ChangeSystemBarsTheme(lightTheme: Boolean) {
        val barColor = PrimaryColor.toArgb()
        LaunchedEffect(lightTheme) {
            if (lightTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        barColor,
                        barColor
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        barColor,
                        barColor
                    )
                )
            } else {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        barColor
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        barColor
                    )
                )
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
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
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

    private fun startService(context: Context) {
        if (isServiceRunning(serviceClass = NeuronMiningService::class.java, context = context)) {
            val serviceIntent = Intent(this, NeuronMiningService::class.java)
            startService(serviceIntent)
        }
    }

    private fun registerBroadcastsReceivers(context: Context) {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(
            InternetConnectivityReceiver(),
            filter
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
