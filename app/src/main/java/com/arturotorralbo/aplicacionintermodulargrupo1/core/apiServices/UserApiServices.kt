package com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices

import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiServices {
    @POST("users/create")
    suspend fun createUser(@Body user: User): Response<Void>

}