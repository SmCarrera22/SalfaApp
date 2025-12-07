package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.domain.model.data.repository.VehiculoRepository
import com.example.salfaapp.network.repository.VehiculoRemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VehiculoViewModel(
    private val localRepo: VehiculoRepository,
    private val remoteRepo: VehiculoRemoteRepository
) : ViewModel() {

    // Lista de vehículos
    val vehiculos: StateFlow<List<VehiculoEntity>> = localRepo.getAllVehiculos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Vehículo seleccionado (para edición)
    private val _vehiculoSeleccionado = MutableStateFlow<VehiculoEntity?>(null)
    val vehiculoSeleccionado: StateFlow<VehiculoEntity?> = _vehiculoSeleccionado

    fun cargarVehiculo(id: Int) {
        viewModelScope.launch {
            val encontrado = localRepo.getVehiculoById(id.toLong())
            _vehiculoSeleccionado.value = encontrado
        }
    }

    fun insertarVehiculo(
        entity: VehiculoEntity,
        onComplete: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                val created = remoteRepo.createOnServer(entity)
                localRepo.guardarVehiculo(created)
                onComplete?.invoke()
            } catch (t: Throwable) {
                onError?.invoke(t)
            }
        }
    }

    fun actualizarVehiculo(
        entity: VehiculoEntity,
        onComplete: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                val updated = remoteRepo.updateOnServer(entity)
                localRepo.guardarVehiculo(updated)
                onComplete?.invoke()
            } catch (t: Throwable) {
                onError?.invoke(t)
            }
        }
    }

    fun eliminarVehiculo(
        entity: VehiculoEntity,
        onComplete: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                remoteRepo.deleteOnServer(entity.id)
                localRepo.eliminarVehiculo(entity)
                onComplete?.invoke()
            } catch (t: Throwable) {
                onError?.invoke(t)
            }
        }
    }
}