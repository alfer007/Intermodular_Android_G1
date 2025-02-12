package com.arturotorralbo.aplicacionintermodulargrupo1.core.repository

import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.ReservaApiService
import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.RoomApiService
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Room
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val roomApiService: RoomApiService,
    private val reservaApiService: ReservaApiService
) {
    private val BASE_URL = "http://10.0.2.2:3000"
    suspend fun getRooms(): List<Room> {
        return roomApiService.getRooms().map { room ->
            room.copy(
                imagenes = room.imagenes.map { "$BASE_URL$it" }
            )
        }
    }

    suspend fun getRoomsByCriteria(startDate: String, endDate: String, numPersonas: Int, extraCama: Boolean): List<Room> {
        return reservaApiService.getAvailableRooms(
            mapOf(
                "fechaInicio" to startDate,
                "fechaSalida" to endDate,
                "numPersonas" to numPersonas.toString(),
                "extraCama" to extraCama.toString()
            )
        ).map { room ->
            room.copy(
                imagenes = room.imagenes.map { "$BASE_URL$it" }
            )
        }
    }
}