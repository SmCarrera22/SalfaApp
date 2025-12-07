package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TallerViewModel(
    private val repository: TallerRepository
) : ViewModel() {

    // Lista de talleres en tiempo real
    val talleres = repository.getAllTalleres()

    // Taller seleccionado (para pantalla de perfil)
    private val _tallerSeleccionado = MutableStateFlow<TallerEntity?>(null)
    val tallerSeleccionado: StateFlow<TallerEntity?> = _tallerSeleccionado

    fun cargarTaller(id: Int) {
        viewModelScope.launch {
            _tallerSeleccionado.value = repository.getTallerById(id)
        }
    }

    fun agregarTaller(t: TallerEntity) {
        viewModelScope.launch {
            repository.insert(t)
        }
    }

    fun actualizarTaller(t: TallerEntity) {
        viewModelScope.launch {
            repository.update(t)
        }
    }

    fun eliminarTaller(t: TallerEntity) {
        viewModelScope.launch {
            repository.delete(t)
        }
    }
}