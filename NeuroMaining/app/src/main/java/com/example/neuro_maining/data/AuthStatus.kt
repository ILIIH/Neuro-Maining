package com.example.neuro_maining.data

sealed class AuthStatus {
    data object Success : AuthStatus()
    class Error(val errorMessage: String) : AuthStatus()
    data object Failed: AuthStatus()
    data object AuthRequired: AuthStatus()
}