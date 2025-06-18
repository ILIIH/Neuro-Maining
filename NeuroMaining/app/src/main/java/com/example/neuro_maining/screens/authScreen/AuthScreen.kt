package com.example.neuro_maining.screens.authScreen

// Android biometric authentication
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt

// AndroidX Compose and lifecycle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Android utilities
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.fragment.app.FragmentActivity
import com.example.neuro_maining.data.AuthStatus
import com.example.neuro_maining.domain.AuthManager


@Composable
fun AuthScreen(authManager : AuthManager ) {
    val context = LocalContext.current
    var authStatus by remember { mutableStateOf("Waiting for authentication...") }
    val activity = LocalContext.current as FragmentActivity

    val authenticators = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
    } else {
        BiometricManager.Authenticators.BIOMETRIC_WEAK
    }
    val biometricManager = BiometricManager.from(context)
    val canAuthenticate = biometricManager.canAuthenticate(authenticators)

    val executor = ContextCompat.getMainExecutor(context)

    val biometricPrompt = remember {
        BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                authManager.authWithBiometry(AuthStatus.Success)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                authManager.authWithBiometry(AuthStatus.Error(errorMessage = errString.toString()))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                authManager.authWithBiometry(AuthStatus.Failed)
            }
        })
    }

    val promptInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // API 30+ (Android 11 and above)
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Authenticate using biometrics or device credentials")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()
    } else {
        // API 29 and below (no DEVICE_CREDENTIAL support with BIOMETRIC_STRONG)
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Authenticate using biometrics")
            .setNegativeButtonText("Cancel") // Required for API < 30
            .build()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = authStatus)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                    biometricPrompt.authenticate(promptInfo)
                } else {
                    Toast.makeText(context, "Biometric not available or not set up", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Authenticate")
        }
    }
}
