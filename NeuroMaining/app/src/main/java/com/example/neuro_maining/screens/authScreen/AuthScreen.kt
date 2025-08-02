package com.example.neuroMaining.screens.authScreen

// Android biometric authentication
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt

// AndroidX Compose and lifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Android utilities
import androidx.compose.foundation.Image
import androidx.core.content.ContextCompat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.FragmentActivity
import com.example.neuroMaining.R

import com.example.neuroMaining.data.AuthStatus
import com.example.neuroMaining.viewModels.AuthScreenViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun AuthScreen(viewModel: AuthScreenViewModel = hiltViewModel(), authUser: () -> Unit) {
    val context = LocalContext.current
    var authStatus = viewModel.authState.collectAsState()
    val activity = LocalContext.current as FragmentActivity
    val authenticators = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.DEVICE_CREDENTIAL
    } else {
        BiometricManager.Authenticators.BIOMETRIC_WEAK
    }
    val biometricManager = BiometricManager.from(context)

    viewModel.setAuthStatus(biometricManager.canAuthenticate(authenticators))

    val executor = ContextCompat.getMainExecutor(context)

    val biometricPrompt = remember {
        BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.authWithBiometry(AuthStatus.SuccessAuth)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    viewModel.authWithBiometry(AuthStatus.FailedAuth)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    viewModel.authWithBiometry(AuthStatus.FailedAuth)
                }
            }
        )
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

    when (authStatus.value) {
        AuthStatus.AuthBiometryRequired -> {
            biometricPrompt.authenticate(promptInfo)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_fingerprint),
                    contentDescription = "Biometry auth icon",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
        AuthStatus.AuthPasswordRequired -> {
            PasswordAuthScreen(
                returnToBiometric = {
                    viewModel.setAuthStatus(biometricManager.canAuthenticate(authenticators))
                },
                validatePasscode = {
                    viewModel.validatePasscode(it)
                }
            )
        }
        AuthStatus.RegistrationRequired -> {
            RegisterAccountScreen {
                viewModel.createPasscode(it)
            }
        }
        AuthStatus.SuccessAuth -> {
            authUser()
        }
        AuthStatus.FailedAuth -> {
            Toast.makeText(context, "Auth error ", Toast.LENGTH_SHORT).show() // TODO change approach
        }
    }
}
