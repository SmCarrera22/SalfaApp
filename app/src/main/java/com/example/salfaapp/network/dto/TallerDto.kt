package com.example.salfaapp.network.dto

data class TallerDto(
    val id: Int? = null,
    val nombre: String,
    val rut: Int,
    val codigoVerificador: Int,
    val tipo: String,
    val direccion: String,
    val encargado: String
)
