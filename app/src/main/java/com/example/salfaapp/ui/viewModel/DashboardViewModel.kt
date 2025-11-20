package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.TipoTaller
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import com.example.salfaapp.domain.model.data.repository.VehiculoRepository
import kotlinx.coroutines.flow.*

class DashboardViewModel(
    private val tallerRepo: TallerRepository,
    private val vehiculoRepo: VehiculoRepository
) : ViewModel() {

    val talleres = tallerRepo.getAllTalleres().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val vehiculos = vehiculoRepo.getAllVehiculos().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    // ----------- TALLERES -----------

    val totalTalleresDyP = talleres.map { list ->
        list.count { it.tipo == TipoTaller.DyP.name }
    }

    val totalTalleresMecanica = talleres.map { list ->
        list.count { it.tipo == TipoTaller.Mecanica.name }
    }


    // ----------- VEHÍCULOS -----------

    val totalStock = vehiculos.map { it.size }

    val vehiculosDisponibles = vehiculos.map { list ->
        list.count {
            it.estado == EstadoVehiculo.Pendiente_Lavado ||
                    it.estado == EstadoVehiculo.Pendiente_Foto ||
                    it.estado == EstadoVehiculo.Disponible
        }
    }

    val vehiculosPAP = vehiculos.map { list ->
        list.count {
            it.estado == EstadoVehiculo.Pendiente_Revision ||
                    it.estado == EstadoVehiculo.Espera_Taller_DyP ||
                    it.estado == EstadoVehiculo.Espera_Taller_Mecanico ||
                    it.estado == EstadoVehiculo.Taller_DyP ||
                    it.estado == EstadoVehiculo.Taller_Mecanica
        }
    }

    val vehiculosNuevos = vehiculos.map { list ->
        list.count { it.estado == EstadoVehiculo.Nuevo_Ingreso }
    }
}