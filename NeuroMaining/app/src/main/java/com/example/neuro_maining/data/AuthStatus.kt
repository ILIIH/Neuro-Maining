package com.example.neuroMaining.data

sealed class AuthStatus {
    data object SuccessAuth : AuthStatus()
    data object FailedAuth : AuthStatus()
    data object AuthPasswordRequired : AuthStatus()
    data object AuthBiometryRequired : AuthStatus()
    data object RegistrationRequired : AuthStatus()
}
