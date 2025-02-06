package com.arturotorralbo.aplicacionintermodulargrupo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.NavigationWrapper
import com.arturotorralbo.aplicacionintermodulargrupo1.ui.theme.AplicacionIntermodularGrupo1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionIntermodularGrupo1Theme {
                NavigationWrapper()
            }
        }
    }
}


