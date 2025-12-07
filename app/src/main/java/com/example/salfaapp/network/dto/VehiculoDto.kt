package com.example.salfaapp.network.dto

data class VehiculoDto(
    val id: Long? = null,
    val marca: String,
    val modelo: String,
    val anio: Int?,
    val tipo: String,
    val patente: String,
    val estado: String,
    val sucursal: String,
    val tallerAsignado: String? = null,
    val observaciones: String? = null
)
