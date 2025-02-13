package com.arturotorralbo.aplicacionintermodulargrupo1.core.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)


    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    fun clearToken() {
        prefs.edit().remove("token").apply()
    }
    fun saveEmail(email: String) {
        prefs.edit().putString("email", email).apply()
        println("Email guardado: $email")
    }

    fun getEmail(): String? {
        return prefs.getString("email", null)
    }

    fun clearEmail() {
        prefs.edit().remove("email").apply()
    }
}

