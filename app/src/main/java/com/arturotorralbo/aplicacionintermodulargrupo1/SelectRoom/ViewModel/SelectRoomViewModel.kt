package com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.ReservaApiService
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Cliente
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Reserva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRoomViewModel @Inject constructor(
    private val reservaApiService: ReservaApiService
) : ViewModel() {
    val startDate = mutableStateOf("")
    val endDate = mutableStateOf("")
    val numberOfGuests = mutableStateOf(1)
    val tipoHabitacion = mutableStateOf("")
    val precio = mutableStateOf(0.0)
    val extrasInt = mutableStateOf(0)
    val idHabitacion = mutableStateOf(0)
    val extraCama = mutableStateOf(false)

    fun crearReserva(userName: String, userEmail: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val formattedStartDate = formatDateToApiFormat(startDate.value)
                val formattedEndDate = formatDateToApiFormat(endDate.value)

                println("🚀 Enviando reserva con:")
                println("idHabitacion: ${idHabitacion.value}")
                println("fechaInicio: $formattedStartDate")
                println("fechaSalida: $formattedEndDate")

                val reserva = Reserva(
                    idHabitacion = idHabitacion.value,
                    cliente = Cliente(nombre = userName, email = userEmail),
                    precio = precio.value,
                    fechaInicio = formattedStartDate,
                    fechaSalida = formattedEndDate,
                    tipoHabitacion = tipoHabitacion.value,
                    numPersonas = numberOfGuests.value,
                    extras = extrasInt.value
                )

                val response = reservaApiService.createReserva(reserva)

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error al crear la reserva: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                onError("Error en la red: ${e.message}")
            }
        }
    }

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

    fun updateExtrasInt(extras: Int) {
        extrasInt.value = extras
    }

    fun updateExtraCama(extra: Boolean) {
        extraCama.value = extra
    }

    fun updateIdHabitacion(id: Int) {
        idHabitacion.value = id
    }

    fun updateDetails(start: String, end: String, guests: Int) {
        updateStartDate(start)
        updateEndDate(end)
        updateNumberOfGuests(guests)
    }

    fun formatDateToApiFormat(date: String): String {
        return try {
            val inputFormatter = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val outputFormatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())

            val parsedDate = inputFormatter.parse(date)
            parsedDate?.let { outputFormatter.format(it) } ?: date
        } catch (e: Exception) {
            date
        }
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

