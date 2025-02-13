package com.arturotorralbo.aplicacionintermodulargrupo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.NavigationWrapper
import com.arturotorralbo.aplicacionintermodulargrupo1.core.utils.TokenManager
import com.arturotorralbo.aplicacionintermodulargrupo1.ui.theme.AplicacionIntermodularGrupo1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenManager = TokenManager(this)
        tokenManager.clearToken()
        tokenManager.clearEmail()
        enableEdgeToEdge()
        setContent {
            AplicacionIntermodularGrupo1Theme {
                NavigationWrapper()
            }
        }
    }
}


