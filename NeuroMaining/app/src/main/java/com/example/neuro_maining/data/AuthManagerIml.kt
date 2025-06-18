package com.example.neuro_maining.data

import android.content.Context
import android.content.SharedPreferences
import com.example.neuro_maining.domain.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit

const val PASSWORD_LOCAL_FILE = "secure_prefs"
const val PASSWORD_LOCAL_FILE_KEY = "local_password"

class AuthManagerIml(private val context: Context): AuthManager {
    private val _authState: MutableStateFlow<AuthStatus> = MutableStateFlow(AuthStatus.AuthRequired)
    override val authState: StateFlow<AuthStatus> = _authState.asStateFlow()

    override fun authWithBiometry(status: AuthStatus) {
        _authState.value = status
    }

    fun getEncryptedPrefs(): SharedPreferences {
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

    override fun authWithPassword(password: String) {
        val sharedPrefs = getEncryptedPrefs()
        val savedPassword: String? = sharedPrefs.getString(PASSWORD_LOCAL_FILE_KEY, null)
        if(password == savedPassword){
            _authState.value = AuthStatus.Success
        }
        else {
            _authState.value = AuthStatus.Failed
        }
    }

    override fun registerPassword(password: String) {
        val sharedPrefs = getEncryptedPrefs()
        sharedPrefs.edit {
            putString(PASSWORD_LOCAL_FILE_KEY, password)
        }

    }
}