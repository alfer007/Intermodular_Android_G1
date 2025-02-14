package com.arturotorralbo.aplicacionintermodulargrupo1.homeView.components

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage

import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Login
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Profile
import com.arturotorralbo.aplicacionintermodulargrupo1.core.utils.TokenManager
import com.arturotorralbo.aplicacionintermodulargrupo1.ui.theme.primaryColorBlue
import openGoogleMaps

@Composable
fun HeaderBar(navController: NavController, context: Context) {
    val tokenManager = remember { TokenManager(context) }
    val token = tokenManager.getToken()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "http://10.0.2.2:3000/images/hotellogoestrellastransparente.png",
            contentDescription = "Logo Hotel",
            modifier = Modifier
                .height(60.dp)
                .width(180.dp),
            contentScale = ContentScale.FillBounds
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconButton(onClick = { openGoogleMaps(context) }) {
                Icon(Icons.Default.GpsFixed, contentDescription = "Teléfono", tint = primaryColorBlue)
            }
            IconButton(onClick = {
                if (token != null) {
                    navController.navigate(Profile)
                } else {
                    navController.navigate(Login)
                }
            }) {
                Icon(Icons.Default.Person, contentDescription = "Perfil/Login", tint = primaryColorBlue)
            }

        }
    }
}