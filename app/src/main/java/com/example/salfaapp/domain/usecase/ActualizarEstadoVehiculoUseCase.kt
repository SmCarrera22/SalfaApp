package com.example.salfaapp.domain.usecase

import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Vehiculo

class ActualizarEstadoVehiculoUseCase(private val listaVehiculos: MutableList<Vehiculo>) {
    operator fun invoke(id: Int, nuevoEstado: EstadoVehiculo) {
        val vehiculo = listaVehiculos.find { it.id == id }
        vehiculo?.estado = nuevoEstado
    }
}