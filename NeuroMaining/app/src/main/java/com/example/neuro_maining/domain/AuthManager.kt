package com.example.neuro_maining.domain

import com.example.neuro_maining.data.AuthStatus
import kotlinx.coroutines.flow.StateFlow

interface AuthManager {
    val authState: StateFlow<AuthStatus>
    fun authWithBiometry(status: AuthStatus)
    fun authWithPassword(password: String)
    fun registerPassword(password: String)
}