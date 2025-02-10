package com.arturotorralbo.aplicacionintermodulargrupo1.core.repository

import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.RoomApiService
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Room
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val apiService: RoomApiService
) {
    private val BASE_URL = "http://10.0.2.2:3000"
    suspend fun getRooms(): List<Room> {
        return apiService.getRooms().map { room ->
            room.copy(
                imagenes = room.imagenes.map { "$BASE_URL$it" }
            )
        }
    }
}