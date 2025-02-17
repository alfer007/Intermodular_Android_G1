package com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Room
import com.arturotorralbo.aplicacionintermodulargrupo1.ui.theme.primaryColorBlue

@Composable
fun RoomCard(room: Room, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = room.imagenes.firstOrNull() ?: "https://i.imgur.com/jzTUxDq.jpeg",
                contentDescription = "Imagen de habitación",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = room.tipoHabitacion, style = MaterialTheme.typography.titleMedium)
            Text(text = "Capacidad: ${room.numPersonas} personas")
            Text(text = "Tamaño: ${room.tamanyo} m²")
            Text(text = "Precio/Noche: ${room.precio}€")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColorBlue
                ),
                ) {
                Text("Ver Detalles")
            }
        }
    }
}