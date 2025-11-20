package com.example.salfaapp.domain.usecase

import com.example.salfaapp.domain.model.Vehiculo

class RegistrarVehiculoUseCase (private val listaVehiculos: MutableList<Vehiculo>) {
    operator fun invoke(vehiculo: Vehiculo) {
        listaVehiculos.add(vehiculo)
    }
}