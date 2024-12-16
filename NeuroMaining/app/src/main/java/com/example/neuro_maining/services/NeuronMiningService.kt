package com.example.neuro_maining.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.neuro_maining.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket


class NeuronMiningService : Service() {

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        setUpClientSocket()
        setUpServerSocket()
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "YourChannelId"
        val channelName = "Foreground Service Channel"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Your Service is running")
            .setContentText("Your foreground service is active and running.")
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        startForeground(1, notification)
    }

    private fun setUpServerSocket(){
        Thread {
            try {
                val serverSocket =
                    ServerSocket(8888) // Choose a port
                val client =
                    serverSocket.accept() // Wait for the client to connect

                // Read data from the client
                val inputStream = client.getInputStream()
                val reader =
                    BufferedReader(InputStreamReader(inputStream))
                val message = reader.readLine() // Example: reading a string
                println("Received: $message")

                // Close connections
                reader.close()
                client.close()
                serverSocket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun setUpClientSocket(){
        Thread {
            try {
                val socket = Socket(
                    "192.168.49.1",
                    8888
                )

                // Send data to the server
                val outputStream = socket.getOutputStream()
                val writer = PrintWriter(outputStream, true)
                writer.println("Hello, server!")

                // Close connection
                writer.close()
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        val restartServiceIntent = Intent(applicationContext, NeuronMiningService::class.java)
        startService(restartServiceIntent)
    }
}
