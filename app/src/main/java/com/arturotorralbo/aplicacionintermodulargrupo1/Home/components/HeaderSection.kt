package com.arturotorralbo.aplicacionintermodulargrupo1.home.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.arturotorralbo.aplicacionintermodulargrupo1.Home.openGoogleMaps
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Login
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Register

@Composable
fun HeaderSection(navController: NavController, context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "http://10.0.2.2:3000/images/hotellogoestrellas.png",
            contentDescription = "Logo Hotel",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconButton(onClick = { openGoogleMaps(context) }) {
                Icon(Icons.Default.GpsFixed, contentDescription = "Teléfono", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Login) }) {
                Icon(Icons.Default.Person, contentDescription = "Login", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Register)}) {
                Icon(Icons.Default.AppRegistration, contentDescription = "Register", tint = Color.White)
            }
        }
    }
}