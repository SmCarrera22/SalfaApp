package com.example.salfaapp.network.api

import retrofit2.http.*
import com.example.salfaapp.network.dto.VehiculoDto

interface VehiculoApi {

    @GET("vehiculos")
    suspend fun getAll(): List<VehiculoDto>

    @GET("vehiculos/{id}")
    suspend fun getById(@Path("id") id: Long): VehiculoDto

    @POST("vehiculos")
    suspend fun create(@Body dto: VehiculoDto): VehiculoDto

    @PUT("vehiculos/{id}")
    suspend fun update(@Path("id") id: Long, @Body dto: VehiculoDto): VehiculoDto

    @DELETE("vehiculos/{id}")
    suspend fun delete(@Path("id") id: Long)

}