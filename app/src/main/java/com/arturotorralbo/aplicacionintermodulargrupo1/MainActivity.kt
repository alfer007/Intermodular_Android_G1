package com.arturotorralbo.aplicacionintermodulargrupo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.NavigationWrapper
import com.arturotorralbo.aplicacionintermodulargrupo1.ui.theme.AplicacionIntermodularGrupo1Theme

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


