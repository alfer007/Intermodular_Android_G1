package com.arturotorralbo.aplicacionintermodulargrupo1.core.models

data class Reserva(
    val idHabitacion: Int,
    val cliente: Cliente,
    val precio: Double,
    val fechaInicio: String,
    val fechaSalida: String,
    val tipoHabitacion: String,
    val numPersonas: Int,
    val extras: Int
)

data class Cliente(
    val nombre: String,
    val email: String
)