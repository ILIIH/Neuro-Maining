package com.example.neuroMaining.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.neuroMaining.domain.AuthManager

const val PASSWORD_LOCAL_FILE = "secure_prefs"
const val PASSWORD_LOCAL_FILE_KEY = "local_password"

class AuthManagerIml(private val context: Context) : AuthManager {
    private fun getEncryptedPrefs(): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PASSWORD_LOCAL_FILE,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun authWithPassword(password: String): Boolean {
        val sharedPrefs = getEncryptedPrefs()
        val savedPassword: String? = sharedPrefs.getString(PASSWORD_LOCAL_FILE_KEY, null)
        return password == savedPassword
    }

    override fun registerPassword(password: String) {
        val sharedPrefs = getEncryptedPrefs()
        sharedPrefs.edit {
            putString(PASSWORD_LOCAL_FILE_KEY, password)
        }
    }

    override fun isPasswordRegistered(): Boolean {
        val sharedPrefs = getEncryptedPrefs()
        val savedPassword: String? = sharedPrefs.getString(PASSWORD_LOCAL_FILE_KEY, null)
        return savedPassword != null
    }
}
