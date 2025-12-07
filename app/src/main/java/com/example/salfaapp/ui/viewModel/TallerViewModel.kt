package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import com.example.salfaapp.network.repository.TallerRemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TallerViewModel(
    private val localRepo: TallerRepository,
    private val remoteRepo: TallerRemoteRepository
) : ViewModel() {

    // Lista de talleres live desde el local DB
    val talleres = localRepo.getAllTalleres()

    private val _tallerSeleccionado = MutableStateFlow<TallerEntity?>(null)
    val tallerSeleccionado: StateFlow<TallerEntity?> = _tallerSeleccionado

    fun cargarTaller(id: Int) {
        viewModelScope.launch {
            // Primero intenta cargar local
            var taller = localRepo.getTallerById(id)

            // Si está vacío, traer desde remoto:
            if (taller == null) {
                val remoto = remoteRepo.getTallerById(id)
                if (remoto != null) {
                    // Guardarlo local
                    localRepo.insert(remoto)
                    taller = remoto
                }
            }

            _tallerSeleccionado.value = taller
        }
    }

    fun agregarTaller(t: TallerEntity) {
        viewModelScope.launch {
            localRepo.insert(t)
            remoteRepo.insert(t)
        }
    }

    fun actualizarTaller(t: TallerEntity) {
        viewModelScope.launch {
            localRepo.update(t)
            remoteRepo.update(t)
        }
    }

    fun eliminarTaller(t: TallerEntity) {
        viewModelScope.launch {
            localRepo.delete(t)
            remoteRepo.delete(t.id)
        }
    }
}