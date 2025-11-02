package com.example.salfaapp.domain.model

class Inventario (
    val id: Int,
    val nombre: String,
    val vehiculos: MutableList<Vehiculo>
) {
}