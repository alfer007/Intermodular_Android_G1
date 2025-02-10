package com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices

import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Reserva
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ReservaApiService {
    @POST("reservas/create")
    suspend fun createReserva(@Body reserva: Reserva): Response<Void>

    @GET("reservas/getAll")
    suspend fun getAllReservas(): Response<List<Reserva>>

    @POST("reservas/filter")
    suspend fun getFilteredReservas(@Body filters: Map<String, Any>): Response<List<Reserva>>

    @PATCH("reservas/update")
    suspend fun updateReserva(@Body reserva: Reserva): Response<Void>

    @POST("reservas/delete")
    suspend fun deleteReserva(@Body id: Map<String, Int>): Response<Void>
}