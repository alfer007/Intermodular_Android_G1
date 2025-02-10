package com.arturotorralbo.aplicacionintermodulargrupo1.core.di

import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.RoomApiService
import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.UserApiServices
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiClient {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun providesUserApiService(retrofit: Retrofit):UserApiServices{
        return retrofit.create(UserApiServices::class.java)
    }
    @Singleton
    @Provides
    fun providesRoomApiService(retrofit: Retrofit): RoomApiService {
        return retrofit.create(RoomApiService::class.java)
    }
}