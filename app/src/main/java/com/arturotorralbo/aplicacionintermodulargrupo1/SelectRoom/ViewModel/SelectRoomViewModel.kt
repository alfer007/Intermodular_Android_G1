package com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SelectRoomViewModel : ViewModel() {
    val startDate = mutableStateOf("")
    val endDate = mutableStateOf("")
    val numberOfGuests = mutableStateOf(1)
    val tipoHabitacion = mutableStateOf("")
    val precio = mutableStateOf(0.0)

    fun updateStartDate(date: String) {
        startDate.value = date
    }

    fun updateEndDate(date: String) {
        endDate.value = date
    }

    fun updateNumberOfGuests(guests: Int) {
        numberOfGuests.value = guests
    }

    fun updateTipoHabitacion(tipo: String) {
        tipoHabitacion.value = tipo
    }

    fun updatePrecio(nuevoPrecio: Double) {
        precio.value = nuevoPrecio
    }

    fun updateDetails(start: String, end: String, guests: Int) {
        updateStartDate(start)
        updateEndDate(end)
        updateNumberOfGuests(guests)
    }

    fun calculateNights(startDate: String, endDate: String): Int {
        return try {
            val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val start = formatter.parse(startDate)
            val end = formatter.parse(endDate)
            if (start != null && end != null && start.before(end)) {
                val diff = end.time - start.time
                (diff / (1000 * 60 * 60 * 24)).toInt()
            } else {
                0
            }
        } catch (e: Exception) {
            0
        }
    }
}

