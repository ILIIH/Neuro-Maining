package com.example.neuroMaining.viewModels

import androidx.biometric.BiometricManager
import androidx.lifecycle.ViewModel
import com.example.neuroMaining.data.AuthStatus
import com.example.neuroMaining.domain.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {
    private val _authState: MutableStateFlow<AuthStatus> = MutableStateFlow(AuthStatus.AuthPasswordRequired)
    val authState: StateFlow<AuthStatus> = _authState.asStateFlow()
    fun setAuthStatus(biometricAuthStatus: Int) {
        if (biometricAuthStatus == BiometricManager.BIOMETRIC_SUCCESS) {
            _authState.value = AuthStatus.AuthBiometryRequired
        } else if (authManager.isPasswordRegistered()) {
            _authState.value = AuthStatus.AuthPasswordRequired
        } else {
            _authState.value = AuthStatus.RegistrationRequired
        }
    }
    fun validatePasscode(code: String) {
        if (authManager.authWithPassword(code)) {
            _authState.value = AuthStatus.SuccessAuth
        } else {
            _authState.value = AuthStatus.FailedAuth
        }
    }
    fun authWithBiometry(biometryAuthStatus: AuthStatus) {
        _authState.value = biometryAuthStatus
    }
    fun createPasscode(code: String) {
        authManager.registerPassword(code)
        _authState.value = AuthStatus.SuccessAuth
    }
}
