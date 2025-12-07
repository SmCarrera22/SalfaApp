package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.domain.model.data.repository.VehiculoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VehiculoViewModel(
    private val repository: VehiculoRepository
) : ViewModel() {

    // Lista completa de vehículos
    private val _vehiculos = MutableStateFlow<List<VehiculoEntity>>(emptyList())
    val vehiculos: StateFlow<List<VehiculoEntity>> = _vehiculos.asStateFlow()

    // Vehículo cargado para edición
    private val _vehiculoSeleccionado = MutableStateFlow<VehiculoEntity?>(null)
    val vehiculoSeleccionado: StateFlow<VehiculoEntity?> = _vehiculoSeleccionado.asStateFlow()

    init {
        cargarVehiculos()
    }

    fun cargarVehiculos() {
        viewModelScope.launch {
            repository.getAllVehiculos().collect {
                _vehiculos.value = it
            }
        }
    }

    fun cargarVehiculo(id: Int) {
        viewModelScope.launch {
            val vehiculo = _vehiculos.value.find { it.id.toInt() == id }
            _vehiculoSeleccionado.value = vehiculo
        }
    }

    fun insertarVehiculo(vehiculo: VehiculoEntity) {
        viewModelScope.launch {
            repository.guardarVehiculo(vehiculo)
        }
    }

    fun actualizarVehiculo(vehiculo: VehiculoEntity) {
        viewModelScope.launch {
            repository.guardarVehiculo(vehiculo) // misma función sirve si usas OnConflict.REPLACE
        }
    }

    fun eliminarVehiculo(vehiculo: VehiculoEntity) {
        viewModelScope.launch {
            repository.eliminarVehiculo(vehiculo)
        }
    }
}