package com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices

import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Room
import retrofit2.http.GET

interface RoomApiService {
    @GET("habitaciones/getAll")
    suspend fun getRooms(): List<Room>
}