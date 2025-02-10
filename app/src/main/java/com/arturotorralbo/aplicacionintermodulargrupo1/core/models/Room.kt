package com.arturotorralbo.aplicacionintermodulargrupo1.core.models

data class Room(
    val idHabitacion: Int,
    val tipoHabitacion: String, // Ahora solo almacenamos el nombre
    val numPersonas: Int,
    val estado: String,
    val tamanyo: Int,
    val descripcion: String,
    val imagenes: List<String>,
    val precio: Double,
    val servicios: List<String> = emptyList()
)
