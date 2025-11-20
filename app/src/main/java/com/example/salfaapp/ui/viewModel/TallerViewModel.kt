package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TallerViewModel(
    private val repo: TallerRepository
) : ViewModel() {

    val talleres: StateFlow<List<TallerEntity>> =
        repo.getAllTalleres().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun agregarTaller(t: TallerEntity) {
        viewModelScope.launch {
            repo.agregarTaller(t)
        }
    }

    fun eliminarTaller(t: TallerEntity) {
        viewModelScope.launch {
            repo.eliminarTaller(t)
        }
    }

    fun getTallerById(id: Int): TallerEntity? {
        return talleres.value.firstOrNull { it.id == id }
    }
}