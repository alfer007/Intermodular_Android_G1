package com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices

import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.User
import com.arturotorralbo.aplicacionintermodulargrupo1.login.CheckUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiServices {
    @POST("users/create")
    suspend fun createUser(@Body user: User): Response<Void>
    @POST("users/login")
    suspend fun checkUser(@Body user: User): Response<CheckUserResponse>

    @PUT("users/update/{email}")
    suspend fun updateUser(
        @Path("email") email: String,
        @Header("Authorization") token: String,
        @Body user: User
    ): Response<User>

    @DELETE("users/delete/{email}")
    suspend fun deleteUser(
        @Path("email") email: String,
        @Header("Authorization") token: String
    ): Response<Void>

    @GET("users/getOne/{email}")
    suspend fun getUser(
        @Path("email") email: String,
        @Header("Authorization") token: String
    ): Response<User>
}