package com.example.neuroMaining.domain

interface AuthManager {
    fun authWithPassword(password: String): Boolean
    fun registerPassword(password: String)
    fun isPasswordRegistered(): Boolean
}
