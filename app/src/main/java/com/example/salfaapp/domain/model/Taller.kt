package com.example.salfaapp.domain.model

open class Taller (
    val id: Int,
    val nombre: String,
    val rut: Int,
    val codigoVerificador: Int,
    val tipo: TipoTaller,
    val direccion: String,
    val encargado: Empleado
) {
}