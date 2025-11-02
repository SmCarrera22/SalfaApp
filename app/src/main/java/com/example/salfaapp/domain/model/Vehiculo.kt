package com.example.salfaapp.domain.model

class Vehiculo (
    val id: Int,
    var marca: String,
    var modelo: String,
    var anio: Int,
    var tipo: String,
    var patente: String,
    var estado: EstadoVehiculo,
    var tallerAsignado: String? = null,
    var observaciones: String? = null
) {
}