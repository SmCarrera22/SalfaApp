package com.example.salfaapp.domain.usecase

import com.example.salfaapp.domain.model.Vehiculo

class AsignarTallerUseCase(private val listaVehiculos: MutableList<Vehiculo>) {
    operator fun invoke(id: Int, taller: String) {
        val vehiculo = listaVehiculos.find { it.id == id }
        vehiculo?.tallerAsignado = taller
    }
}