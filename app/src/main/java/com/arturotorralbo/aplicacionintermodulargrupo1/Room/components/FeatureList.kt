package com.arturotorralbo.aplicacionintermodulargrupo1.Room.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color


@Composable
fun FeatureList(
    numPersonas: Int,
    tamanyo: Int,
    servicios: List<String>
) {
    val features = listOf(
        "${numPersonas} huéspedes",
        "${tamanyo} m²"
    ) + servicios

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(features) { feature ->
            FeatureTag(text = feature)
        }
    }

    if (servicios.isEmpty()) {
        Text(
            text = "No hay servicios disponibles",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}