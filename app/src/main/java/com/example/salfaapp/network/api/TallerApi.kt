package com.example.salfaapp.network.api

import retrofit2.http.*
import com.example.salfaapp.network.dto.TallerDto

interface TallerApi {
    @GET("talleres")
    suspend fun getAll(): List<TallerDto>

    @GET("talleres/{id}")
    suspend fun getById(@Path("id") id: Int): TallerDto

    @POST("talleres")
    suspend fun create(@Body dto: TallerDto): TallerDto

    @PUT("talleres/{id}")
    suspend fun update(@Path("id") id: Int, @Body dto: TallerDto): TallerDto

    @DELETE("talleres/{id}")
    suspend fun delete(@Path("id") id: Int)
}