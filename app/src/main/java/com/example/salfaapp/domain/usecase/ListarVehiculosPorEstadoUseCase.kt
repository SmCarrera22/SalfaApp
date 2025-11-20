package com.example.salfaapp.domain.usecase

import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Vehiculo

class ListarVehiculosPorEstadoUseCase(private val listaVehiculos: MutableList<Vehiculo>) {
    operator fun invoke(estado: EstadoVehiculo): List<Vehiculo> {
        return listaVehiculos.filter { it.estado == estado }
    }
}