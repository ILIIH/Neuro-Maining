package com.example.neuro_maining

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.neuro_maining.broadcast_receivers.InternetConnectivityReceiver
import com.example.neuro_maining.services.NeuronMiningService
import com.example.neuro_maining.ui.theme.NeuroMainingTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(context = applicationContext)
        registerBroadcastsReceivers(context = applicationContext)
        promptEnableWifi(context = applicationContext)
        setContent {
            NeuroMainingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Hello =)")
                }
            }
        }
    }

    private fun promptEnableWifi(context: Context) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            AlertDialog.Builder(context)
                .setTitle("Enable WiFi")
                .setMessage("This feature requires WiFi to be enabled. Would you like to enable it?")
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)
                    context.startActivity(intent)
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
        context.registerReceiver(InternetConnectivityReceiver(), filter)

    }
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